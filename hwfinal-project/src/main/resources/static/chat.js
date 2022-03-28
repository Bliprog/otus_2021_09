
    var stompClient = null;
    var username = null;
    var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
    ];

    async function getAllMessages(){
    var messageArea = document.getElementById('messageArea');
    await fetch(
    "http://localhost:8080/chat/get_chat_messages",
{
    method: "GET",
    credentials: 'include'

}
    ).then(response=>response.json()).then(json=>{
    let messages =json.messages;
    username = json.username;
    messages.forEach(function(item, i, arr){
    var messageElement = document.createElement('li');
    messageElement.classList.add('chat-message');
    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(item.sender[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(item.sender);
    messageElement.appendChild(avatarElement);
    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(item.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);
    var textElement = document.createElement('p');
    var messageText = document.createTextNode(item.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
})
    console.log(messages)

}).catch((err=>console.log(err)));
}
    async function connect(event) {
    await getAllMessages();
    if(username) {
    var chatPage = document.getElementById('chat-page');
    chatPage.classList.remove('hidden');
    var socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, await onConnected, onError);
}
    event.preventDefault();
}
    async function onConnected() {
    var connectingElement = document.querySelector('.connecting');
    var messageForm = document.getElementById('messageForm');
    messageForm.addEventListener('submit', sendMessage, true);
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
{},
    JSON.stringify({sender: username, type: 'JOIN'})
    )
    connectingElement.classList.add('hidden');


}
    function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}
    function sendMessage(event) {
    var messageInput = document.getElementById('message');
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
    var chatMessage = {
    sender: username,
    content: messageInput.value,
    type: 'CHAT'
};
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    messageInput.value = '';
}
    event.preventDefault();
}
    function onMessageReceived(payload) {
    var messageArea = document.getElementById('messageArea');
    var message = JSON.parse(payload.body);
    var messageElement = document.createElement('li');
    if(message.type === 'JOIN') {
    messageElement.classList.add('event-message');
    message.content = message.sender + ' joined!';
} else if (message.type === 'LEAVE') {
    messageElement.classList.add('event-message');
    message.content = message.sender + ' left!';
} else {
    messageElement.classList.add('chat-message');
    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(message.sender[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.sender);
    messageElement.appendChild(avatarElement);
    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);
}
    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
    function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
    hash = 31 * hash + messageSender.charCodeAt(i);
}
    var index = Math.abs(hash % colors.length);
    return colors[index];
}
    window.addEventListener('load', connect, true);
