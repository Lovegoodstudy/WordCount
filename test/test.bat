java WordCount -c test1.txt
java WordCount -c -w test1.txt
java WordCount -c -w test2.txt
java WordCount -c -w -l test2.txt
java WordCount -c -w -l test3.txt
java WordCount -c -w -l -a test3.txt
java WordCount -c -w -l -a -s *.txt
java WordCount -c -w -l -a -e stopList.txt test1.txt
java WordCount -c -w -l -a -e stopList.txt test2.txt
java WordCount -c -w -l -a -e stopList.txt test3.txt