># **Finance Tracker**

## Description and Purpose
This **Finance Tracker** is designed to provide a way for users to manage their personal finances in an *effective* way. Users should be able to track their expenses, and savings to develop positive spending habits through representation in a visually organized approach.
## Target Users
Anyone who has personal finances set up should be able to make use of this application. It is not only useful for people who frequently make purchases and are looking for a way to visually understand their finances, but it is also useful for people who are looking to set financial goals for the future, even if they may not have the means to accomplish those goals in the present moment.

*Examples of Possible Users:*
- Students
- Professionals
- Parents 
- Small Business Owners 
- People Saving for a Goal 
  - Planning Trips
  - Paying off Debt
  - Building an Emergency Fund
- Organizations 
- **and More**
 
 ### Why?
 I created project due to its real-world applications. Stress surrounding managing money is a very prevalent issue among almost everyone at some point in their lives. I hope through projects like these, people can alleviate some of that pressure by being aware of what is occurring with their finances and how to approach common challenges. On a technical side, I chose this project due to its expandability, as new features can be implemented and also removed without changing the overall functionality of the application. 

  ## User Stories
 - As a user, I want to be able to create a new category of expenses 
 - As a user, I want to be able to add purchases to a category of my expenses
 - As a user, I want to be able to select a category of expenses and view a list of the purchases in that category 
 - As a user, I want to be able to select a purchase and view the details of that purchase
 - As a user, I want to be able to create savings categories
 - As a user, I want to be able to add money to the savings catagories
 - As a user, I want to be able to delete purchases from a category of my expenses 
 - As a user, I want to be able to save my expenses and savings to file (if I so choose)
 - As a user, I want to be able to load my expenses and savings from file (if I so choose)

 ## Instructions for End User
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking on an added purchase and pressing "Delete Purchase" button
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the "Filter: > $100" button in purchases to only see purchases that costed more than $100
- You can locate my visual component by starting the application and an intro image will show up. Or you can see total, save, or load, to see the icon in the pop up
- You can save the state of my application by clicking the button at the top that is labelled "Save Data"
- You can reload the state of my application by by clicking the button at the top that is labelled "Load Data"

## Phase 4: Task 2
Wed Mar 26 23:24:46 PDT 2025
Added purchase to expense category: sandwich


Wed Mar 26 23:24:46 PDT 2025
Added purchase to expense category: chips


Wed Mar 26 23:24:46 PDT 2025
Added purchase to expense category: coffee


Wed Mar 26 23:24:46 PDT 2025
Added purchase to expense category: shoes


Wed Mar 26 23:24:46 PDT 2025
Added purchase to expense category: hat


Wed Mar 26 23:24:46 PDT 2025
Added purchase to expense category: lights


Wed Mar 26 23:24:46 PDT 2025
Added purchase to expense category: textbook


Wed Mar 26 23:25:08 PDT 2025
Added purchase to expense category: calculator


Wed Mar 26 23:25:17 PDT 2025
Added purchase to expense category: supplies


Wed Mar 26 23:25:23 PDT 2025
Toggled filter for purchases


Wed Mar 26 23:25:27 PDT 2025
Removed purchase from expense category: supplies


Wed Mar 26 23:25:29 PDT 2025
Removed purchase from expense category: calculator


Wed Mar 26 23:25:32 PDT 2025
Removed purchase from expense category: textbook


Wed Mar 26 23:25:43 PDT 2025
Added $1000.0 to total savings in category


Wed Mar 26 23:26:05 PDT 2025
Added $50.0 to total savings in category


Wed Mar 26 23:26:12 PDT 2025
Removed purchase from expense category: sandwich


Wed Mar 26 23:26:28 PDT 2025
Toggled filter for purchases

## Phase 4: Task 3
Reflecting on the my UML diagram's design, I noticed that the "Expense" and "Saving" classes have a lot of the same structures. If I had more time to work on the project, I would focus on refactoring the "Expense" and "Saving" classes. The reason I would choose this area to focus on is based off their shared functionality and features, along with them being a key part of the whole application. Currently, while they have some differences, they have the general function of filling in the details of money that would be leaving/stored and then adding it into something which holds it. In this case, purchases are held in "Expense" and savings are held in "Saving." This change removes redundancy and would make it easier to perform similar actions on the categories such as add, remove etc. while also allowing for future growth. If I wanted to add a new category such as investments, I would be able to easily implement it through taking advantage of the shared features. 

To perform this refactoring I would create a new abstract class titled "FinancialCategory" which has the purpose of acting as a template and to hold the shared functionality between different categories of ways that one might spend or keep track of money. For my current code, "Expense" and "Saving" would extend this class and inherit any non-abstract methods from "FinancialCategory." Methods such as adding and removing could be redesigned in savings to be objects in a list instead of contributing to a total amount of money. This would keep track of when contributions were made and also allow it to be refactored from both classes into the superclass as a shared method. Repeated getters and setters such as the name could also be moved to the superclass. Furthermore, this would also make it easier for other classes to use the code, such as when viewing or filtering. 