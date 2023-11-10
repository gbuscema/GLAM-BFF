from rembg import remove
import argparse


def main(input_path, output_path):
    print("Removing background from " + input_path + " to " + output_path)
    with open(input_path, 'rb') as i:
        print("Opened input file")
        with open(output_path, 'wb') as o:
            print("Opened output file")
            input_img = i.read()
            output = remove(input_img)
            o.write(output)
            print("Wrote output file")


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('arg1', help='Input image')
    parser.add_argument('arg2', help='Output image')
    args = parser.parse_args()
    main(args.arg1, args.arg2)
