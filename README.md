# Readme

Low-level dispatcher simulator written in Java/JavaFX. Pre-populates with some processess at each priority level, but this can be changed easily via global variables at the top of `DispatcherGUI.java`. Same with # of priority levels -- everything should adapt fine if a different # of priorities is desired.

*All* actions and context switches are triggered manually. The only "automated" element is the dispatcher properly choosing the next process based on the state of queues and blocked/running processes. Clicking on a process should bring up status and details in the top right display. Processes can be terminated, interrupted (put back in queue), or added/removed from the block list (only from running state). 

* Build with maven (default target is 'package')
* Basic unit tests included for data structures.
* Woeful lack of documentation, but variables and methods are reasonably well-named. 

