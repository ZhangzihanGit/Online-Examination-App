# Meeting Minutes

## Meeting Information
**Meeting Date/Time:** 24/08/2020, week4, 1pm  
**Meeting Purpose:** Git Workflow    

## Attendees
People who attended:
- Zihan Zhang
- Chegneng Liu
- Hao Ding

## Discussion Items

### Git Workflow
Create following branches:
* `master` stable, used to deploy the last release
* `dev` serves as an integration branch for features
* `[username]/feature/[taskname]` dedicate to develops new features
* `[username]/bugfix/[taskname]` dedicate to fix bugs
* `[username]/chore/[taskname]` for any other tasks

### `.gitignore`
A file specifies intentionally untracked files that Git should ignore. It is important to ignore build files, cache files, etc. in version control.