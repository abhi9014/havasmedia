@BDDSTORY-ZAP-6
Feature: CI Integration With Zaphyr and JIRA 
To Test Automation Execution with Stories Created in Zaphyr


	@BDDTEST-ZAP-7
	@BDDVER--1
	@BDDCYC-b49c33ac-ac6a-4c1e-9c98-e518daa8b2bc
	Scenario Outline: Simple Test Scenario
	
		
		Given user enters valid "<userName>" and "<password>"
		And user clicks on LoginButton
		Then Verify the UserName in Homepage
		
		Examples:
		|userName | password|
		|125927  | Achat9002*|
