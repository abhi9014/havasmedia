@havasMediaCampaignPage
Feature: generating Extent Report with Cucumber 

@test
Scenario Outline: Run a sample Test To Navigate GerHR
Given user enters valid "<userName>" and "<password>"
And user clicks on LoginButton
Then Verify the UserName in Homepage

Examples:
|userName | password|
|****  | ******|


