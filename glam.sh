function Install-Glam {
    GlamPath=$(dirname "$0")
    #/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    brew install helm
    brew install kubernetes-cli
    helm repo add milvus https://zilliztech.github.io/milvus-helm/
    helm repo update
    helm upgrade -i glam-milvus "$GlamPath/helm/milvus/charts/milvus"
}

Install-Glam