# Installation & Usage

### Docker
*built:* ` sudo docker build --no-cache -t abc2 .`  
*run:* `sudo docker run -d -p 9000:9000 abc2  `

visit the results on **localhost:9000**

### Maven built
  
*built from Java source files*
- make sure Maven-3 and opendjdk-8 are installed on the host-machine 
- run `mvn clean` and `mvn install` in the projects directory (the one with the pom.xml)
- to create an executable .jar run `m̀vn package` in the same directory
- finally you can run `java -jar /path/to/executable.jar` to do a runOff Simulation
- the results of the simulation are stored in the `logs` folder

**Extra**
- furthermore you can run `pytho3 vis.py /path/to/logfile` and `python3 myhttp.py` to serve the 
    results on localhost:9000 
    
 #### if the results are served with the minimal http-server make sure port 9000 on the hostmachine is availabe (sometimes oracle uses this port as default)