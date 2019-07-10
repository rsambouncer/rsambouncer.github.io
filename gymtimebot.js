function sendMessage() {
  // Make a POST request with a JSON payload.
  var data = {
    "bot_id"  : "[insert id here]",
    "text"    : "Anyone want to go to the gym tommorow?"
  }
  var options = {
    'method' : 'post',
    // Convert the JavaScript object to a JSON string.
    'payload' : JSON.stringify(data)
  };
  UrlFetchApp.fetch('https://api.groupme.com/v3/bots/post', options);
}
