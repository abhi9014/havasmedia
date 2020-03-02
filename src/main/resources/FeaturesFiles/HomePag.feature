@BDDSTORY-TES-3
Feature: Test Zaphyr Configuration



	@BDDTEST-TES-4
	@BDDVER--1
	@BDDCYC-b62129c4-8999-4a89-8daa-86da6e789de9
	Scenario Outline: TestAutomationExecution
	
		
			
				Given user enters valid "<userName>" and "<password>"
				And user clicks on LoginButton
				Then Verify the UserName in Homepage
				
				Examples:
				|userName | password|
				|125927  | Achat9002*|
		