Feature: The Leaderboard shows a ranking with all the users who solved
  challenges correctly. It displays them ordered by the highest score first.

  Scenario: Users get points and badges when solving challenges, and they
  are positioned accordingly in the Leaderboard.
    Given the following solved challenges
      | user  | solved_challenges |
      | Karen | 5                 |
      | Laura | 7                 |
    Then Karen has 50 points
    * Karen has the "First time" badge
    And Laura has 70 points
    * Laura has the "First time" badge
    * Laura has the "Bronze" badge
    And Laura is above Karen in the ranking


