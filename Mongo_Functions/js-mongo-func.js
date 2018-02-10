// function that adds a sent message to db
function sendMessage(chatID, senderMobileNumber, type, geographicalLocation, data, status, replyingToMessage, payload) {
  var doc = {
    chatID: chatID,
    senderMobileNumber: senderMobileNumber,
    type: type,
    geographicalLocation: geographicalLocation,
    data: data,
    status: status,
    replyingToMessage: replyingToMessage,
    payload: payload
  }
  db.collection("messages").insertOne(doc, function(err, res) {
    if (err) throw err;
  });
}


// function that adds a story to db
function postStory(mobileNumber, data, duration, type, expirationDate, viewedBy) {
  var doc = {
    mobileNumber: mobileNumber,
    data: data,
    duration: duration,
    type: type,
    expirationDate: expirationDate,
    viewedBy: viewedBy
  }
  db.collection("stories").insertOne(doc, function(err, res) {
    if (err) throw err;
  });
}
