## Theoretical part

### 1. Which of the following activities cannot be automated
- [ ] Test execution
- [X] Exploratory testing
- [X] Discussing testability issues
- [ ] Test data generation

### 2. How do we describe a good unit test?
- [ ] Flawless, Ready, Self-healing, True, Irresistible
- [ ] Red, Green, Refactor
- [X] Fast, Repeatable, Self-validating, Timely, Isolated
- [ ] Tests should be dependent on other tests

### 3. When is it a good idea to use XPath selectors
- [X] When CSS or other selectors are not an option or would be brittle and hard to maintain
- [ ] When we need to find an element based on parent/child/sibling relationship
- [ ] When an element is located deep within the HTML (or DOM) structure
- [ ] All the above

### 4. Describe the TDD process

TDD is a software development process, where we write tests before implementation. After which we refactor the code if needed and continue the cycle.
![TDD](red-green-refactor.png)
By the words of Robert C. Martin we trust our tests more if the TDD process is followed correctly.

### 5. Write 2 test cases or scenarios for a String Calculator application, which has a method ```calculate()``` that takes a string of two numbers separated by a comma as input, and returns the sum.

- **Given** the input "1.5,5.5" **When** the method ```calculate()``` is called **Then** I should see "7" as a result.  
- **Given** the input "-1,5" **When** the method ```calculate()``` is called **Then** I should see "4" as a result.  

Some more:
- **Given** the input "" **When** the method ```calculate()``` is called **Then** "Invalid Input Exception" is thrown as a result.  
- **Given** the input "a,4" **When** the method ```calculate()``` is called **Then** "Invalid Input Exception" is thrown as a result.  
- **Given** the input ",6" **When** the method ```calculate()``` is called **Then** "Invalid Input Exception" is thrown as a result.  
- **Given** the input "2147483647,1" **When** the method ```calculate()``` is called **Then** I should see "2147483648" as a result.  
- **Given** the input "0.1111111111111111111,1" **When** the method ```calculate()``` is called **Then** I should see "1.1111111111111111111" as a result.  
