import os

dir = r"E:\basssssss\DatabaseCourseDesign\src\main"

filter_ext = ["pyc","json","xlsx","csv"]

total_lines = 0


def cacu(path):
    ext = path.split(".")[-1]
    if ext in filter_ext:
        return 0
    print(path)
    global total_lines
    try:
        with open(path, "r", encoding="utf-8")as f:
            for _ in f:
                total_lines += 1
    except:
        pass


def cnt(directory):
    if os.path.isdir(directory):
        for tmp_dir in os.listdir(directory):
            cnt(os.path.join(directory, tmp_dir))
    else:
        cacu(directory)


if __name__ == '__main__':
    cnt(dir)
    print(total_lines)
