@BDDSTORY-ZAP-10
Feature: ToTestBDDWithZephyr



	@BDDTEST-ZAP-11
	@BDDVER--1
	@BDDCYC-55d2b0dc-7590-4b67-a39e-ec7c9882282d
	Scenario Outline: SimpleBddTest
	
		Given user enters valid "<userName>" and "<password>"
		And user clicks on LoginButton
		Then Verify the UserName in Homepage
		
		Examples:
		|userName | password|
		|125927  | Achat9002*|

