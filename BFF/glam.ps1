function Install-Glam
{
    $GlamPath=$PSScriptRoot

    Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
    choco install kubernetes-helm
    choco upgrade kubernetes-cli

    helm repo add milvus https://zilliztech.github.io/milvus-helm/
    helm repo update
    
    helm upgrade -i glam-milvus "$GlamPath\helm\milvus\charts\milvus"
}

Install-Glam