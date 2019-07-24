@ignore
Feature: Common functions

  Scenario: Common functions

    * def Utils = Java.type('GUITests.utils.MyDriver')

    * def currentDate =
          """
              function(){
                return new Date().toISOString().slice(0,10);
              }
          """

                # Function generates date in YYYY-MM-DD'T'HH:mm:ss.SSS'Z' format in the future based on current date and supplied parameter
    * def futureDate =
            """
          function(days){
            return new Date(new Date().setDate(new Date().getDate() + days)).toISOString().slice(0,10);
            }
           """

            # Function generates date in YYYY-MM-DD'T'HH:mm:ss.SSS'Z' format in the past based on current date and supplied parameter
    * def pastDate =
            """
          function(days){
            return new Date(new Date().setDate(new Date().getDate() - days)).toISOString().slice(0,10);
            }
           """

    * def futureDateISOString =
            """
          function(days){
            return new Date(new Date().setDate(new Date().getDate() + days)).toISOString().slice(0,10);
            }
           """

            # Function generates date in YYYY-MM-DD'T'HH:mm:ss.SSS'Z' format in the past based on current date and supplied parameter
    * def pastDateISOString =
            """
          function(days){
            return new Date(new Date().setDate(new Date().getDate() - days)).toISOString().slice(0,10);
            }
           """

    * def randomString =
    """
    function randomString(amount) {
      var text = "";
      var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
      for (var i = 0; i < amount; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));
      return text;
}
    """

    * def randomInt =
    """
    function randomInt(amount) {
      var text = "";
      var possible = "0123456789";
      for (var i = 0; i < amount; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));
      return text;
}
    """

    * def randomSpecialSymbols =
"""
    function randomSpecialSymbols(amount) {
      var text = "";
      var possible = "!@#$%^&*()_+-=|\?/€[]{};:.>,<`";
      for (var i = 0; i < amount; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));
      return text; }
    """

    * def sessionHeaders = {"Origin":"http://computer-database.herokuapp.com",  "Content-Type":"application/x-www-form-urlencoded","Accept":"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3"}