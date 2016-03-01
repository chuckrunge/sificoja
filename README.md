# sificoja
Simple File Compare for Java

Copyright (C) 2016  Chuck Runge
Lombard, IL.
CGRunge001@GMail.com

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. 

*===============================================*
sificoja
*===============================================*

Sificoja will compare two text files and display the differences.  The assumption is that file #1 is the base version, and the delta (changed) version is file #2.  Records that are the same are ignored.  Differences, including additional records, are displayed with specific changes identified.

Typical uses would be to validate minor changes, such as deployment of a properties file.  Or in the case of software testing, to compare a "before" version with an "after" version to insure that the changes are expected.  

RELEASE NOTES - 1.0.1
The FileCompare object was rewritten using memory arrays.  This allows a flexible way to "read ahead" for a match, bypassing any inserts in file2.  A properties file, sificoja.props, was added to fine-tune compare options.  Options include:
1) readAheadNbr - read-ahead up to a specific number of lines to find a matching line.  Use the smallest number you can.
2) sort - sort both files before matching.
3) pctForMatch - percent of matching characters that will count a line as a match. If minor changes don't matter, this will screen them out. 
4) maxSize will automatically size the arrays for performance.  
A future enhancement will be to provide an update facility in the GUI. 

A report file is written to "Compare.txt" in the same folder as the application.  Differences are displayed in the GUI and on the command line, but large reports are easier to review in your favorite notepad.  And they can be archived if proof of testing is required.

In the GUI, pressing the File1 button will display a File Open dialog for selecting the base file.  The File2 button does the same for the delta file.  Pressing Compare will execute the function.

To run the GUI, the following command should be executed:
java -cp sificoja-1.0.1-RELEASE.jar com/cgr/javaGUI/MainWindow

At the command line, specify file1 and file2 as parameters following the name of the program (BatchInterface).
For batch execution, the command is as follows:
java -jar sifacoja-1.0.1-RELEASE.jar BatchInterface %1 %2
where the parameters are the respective file names.
