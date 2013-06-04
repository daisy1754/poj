#!/usr/bin/python

# Wrapper of oj.py script to deal with Java or POJ specific issues.
# You can 
# 1) create boilerplate Java file with given class Name
# 2) submit Java file whose class name is not Main
# You MUST place this script in the same folder with oj.py
#
# To generate new *.java file from Template.java:
# > poj.py -g ClassName
# To test your java file:
# > poj.py -i FILE_NAME -t PROBLEM_NO
# To submit your java file:
# > poj.py -i FILE_NAME -s PROBLEM_NO
# To remove tempoary files created by oj.py or this script:
# > poj.py -c

from optparse import OptionParser
import commands
import os
import re

class_def_pattern = re.compile(r'class [a-zA-Z][a-zA-Z0-9]+ {')

def rename_java_class(original_class, new_class):
    original_file = open(original_class + ".java")
    new_file = open(new_class + ".java", "w")

    lines = original_file.readlines()
    original_file.close()
    class_name_rewrited = False
    for line in lines:
        if (not class_name_rewrited) and class_def_pattern.search(line):
            new_line = class_def_pattern.sub("class " + new_class + " {", line)
            new_file.write(new_line)
            class_name_rewrited = True
        else:
            new_file.write(line)
    new_file.close()

def print_error(message):
    print "Error: " + message

def execute_oj_script(options):
    path_to_oj_script = os.path.dirname(__file__) + "/oj.py"
    if not os.path.exists(path_to_oj_script):
        print_error("oj.py cannot found. oj.py MUST be placed in same directory with this script")
        return
    command_str = path_to_oj_script + " --poj " + " ".join(options)
    print command_str
    print commands.getoutput(command_str)

def gen_template(gen_template_name):
    rename_java_class("Template", gen_template_name)

def update_submitted_file_info(file_name, problem_no):
    submitted_list_file_name = "submitted_files.tsv"
    if os.path.isfile(submitted_list_file_name):
        submitted_list_file = open(submitted_list_file_name, "r")
        lines = submitted_list_file.readlines()
        submitted_list_file.close()
    else:
        lines = []
    submitted_list_file = open(submitted_list_file_name, "w")
    is_recorded = False
    new_line = problem_no + "\t" + file_name + "\n"
    problem_no = int(problem_no)
    for line in lines:
        if (int(line.split("\t")[0]) > problem_no and not is_recorded):
            submitted_list_file.write(new_line)
            is_recorded = True
        elif (int(line.split("\t")[0]) == problem_no):
            is_recorded = True
        submitted_list_file.write(line)
    if not is_recorded:
        submitted_list_file.write(new_line)
    submitted_list_file.close()

def submit(file_name, problem_no):
    if file_name == None:
        print_error("Please specify file name")
        return
    rename_java_class(file_name.split(".")[0], "Main")
    execute_oj_script(["-s -i Main.java", problem_no])
    update_submitted_file_info(file_name, problem_no)

def test(file_name, problem_no):
    if file_name == None:
        print_error("Please specify file name")
        return
    execute_oj_script(["-i", file_name, problem_no])

def clean_data_files():
    commands.getoutput("rm *.in.txt *.out.txt out.txt *.class Main.java")

def main():
    parser = OptionParser()

    parser.add_option("-c", "--clean-date-files", action="store_true",
                      dest="clean_data", default=False,
                      help="clean data files generated for test")
    parser.add_option("-g", "--generate-template", action="store",
                      dest="gen_template_name", default=None,
                      help="Generate template file with given class name")
    parser.add_option("-i", "--source-file-name", action="store",
                      dest="source_file_name", default=None,
                      help="Specify source file")
    parser.add_option("-s", "--submit", action="store_true",
                      dest="submit", default=False,
                      help="submit the solution")
    parser.add_option("-t", "--test", action="store_true",
                      dest="test", default=False,
                      help="execute test")

    (options, args) = parser.parse_args()
 
    if options.clean_data:
        clean_data_files()
    elif options.gen_template_name:
        gen_template(options.gen_template_name)
    elif options.submit:
        submit(options.source_file_name, args[0])
    elif options.test:
        test(options.source_file_name, args[0])
    else:
        parser.print_help()
   
main()
