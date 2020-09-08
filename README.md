# Project Reimbursement

## Compile From Source

This project uses gradle as its dependency management tool.  Included in the source is the gradle wrapper

To build using the gradle wrapper, simply clone the repo and run `gradlew clean shadowJar` in the root.

This will create a file in the build/libs folder (relative to the root) called project-reimbursement.jar

## Run

<pre>
usage: java -jar project-reimbursement.jar
  -f,--filename &lt;arg&gt;   Specify a file for input
  -h,--help             print this message
  -l,--loglevel &lt;arg&gt;   Specify a log level
</pre>

Setting the log level to "INFO" will output the result of a given set/scenario.
Setting the log level to "DEBUG" will output a more robust description of what is happening.

Running without a filename will run the "scenarios/allScenarios.json" file in the resources folder.

Running with one of the following filenames will result in that specific file resource being run:
* java -jar project-reimbursement.jar -f scenarios/scenario1.json
* java -jar project-reimbursement.jar -f scenarios/scenario2.json
* java -jar project-reimbursement.jar -f scenarios/scenario3.json
* java -jar project-reimbursement.jar -f scenarios/scenario4.json
* java -jar project-reimbursement.jar -f scenarios/allScenarios.json

## Problem Description

You have a set of projects, and you need to calculate a reimbursement amount for the set. Each project has a start date and an end date. The first day of a project and the last day of a project are always "travel" days. Days in the middle of a project are "full" days. There are also two types of cities a project can be in, high cost cities and low cost cities. 

A few rules:
First day and last day of a project, or sequence of projects, is a travel day.
Any day in the middle of a project, or sequence of projects, is considered a full day.
If there is a gap between projects, then the days on either side of that gap are travel days.
If two projects push up against each other, or overlap, then those days are full days as well.
Any given day is only ever counted once, even if two projects are on the same day.
A travel day is reimbursed at a rate of 45 dollars per day in a low cost city.
A travel day is reimbursed at a rate of 55 dollars per day in a high cost city.
A full day is reimbursed at a rate of 75 dollars per day in a low cost city.
A full day is reimbursed at a rate of 85 dollars per day in a high cost city.

Given the following sets of projects, provide code that will calculate the reimbursement for each.

* Set 1:
  * Project 1: Low Cost City Start Date: 9/1/15 End Date: 9/3/15

* Set 2:
  * Project 1: Low Cost City Start Date: 9/1/15 End Date: 9/1/15
  * Project 2: High Cost City Start Date: 9/2/15 End Date: 9/6/15
  * Project 3: Low Cost City Start Date: 9/6/15 End Date: 9/8/15

* Set 3:
  * Project 1: Low Cost City Start Date: 9/1/15 End Date: 9/3/15
  * Project 2: High Cost City Start Date: 9/5/15 End Date: 9/7/15
  * Project 3: High Cost City Start Date: 9/8/15 End Date: 9/8/15

* Set 4:
  * Project 1: Low Cost City Start Date: 9/1/15 End Date: 9/1/15
  * Project 2: Low Cost City Start Date: 9/1/15 End Date: 9/1/15
  * Project 3: High Cost City Start Date: 9/2/15 End Date: 9/2/15
  * Project 4: High Cost City Start Date: 9/2/15 End Date: 9/3/15
