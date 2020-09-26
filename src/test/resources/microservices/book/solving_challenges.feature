Feature: Solve multiplication challenges
  We present users with challenges they should solve using mental calculation
  only. When they're right, we give them score and, in some cases, new badges.
  All attempts from all users are stored, so they can see their historical data.

  Scenario: Users get new attempts.
    Given a new user Mary
    When she requests a new challenge
    Then she gets a mid-complexity multiplication to solve

  Scenario: Users solve challenges, they get feedback and their stats.
    Given a new user John
    And he requests a new challenge
    When he sends the correct challenge solution
    Then his stats include 1 correct attempt

  Scenario: Users get feedback also about incorrect attempts.
    Given a new user Horatio
    When he requests a new challenge
    * he sends the incorrect challenge solution
    * he sends the correct challenge solution
    Then his stats include 1 correct attempt
    * his stats include 1 incorrect attempts
