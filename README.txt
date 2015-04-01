Requirements:
1. Apache Maven 3.2.5 or higher
2. Git shell

Instruction:
1. Open Git shell.
2. Clone repository with 'git clone https://github.com/bogdanlupashko/parser.git <destination folder>' command.
3. Go to <destination folder> with OS command line.
4. With OS command line Run: 'mvn clean compile assembly:single'
5. Go to 'target' subdirectory with OS command line.
6. With OS command line run: 'java -jar qname-1.0-SNAPSHOT-jar-with-dependencies.jar <name>'
(where <name> is prefixed name or simple name)