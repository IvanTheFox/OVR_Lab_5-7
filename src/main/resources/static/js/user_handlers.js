async function fetchUserById(id) {
    try {
        const response = await fetch(`http://localhost:8089/userinfobyid/${id}`); 
        if (!response.ok) {
            document.getElementById("response-text").innerHTML="Такого пользователя нет!";
            return null;
        }
        const data = await response.json(); 
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function fetchUserByName(name) {
    try {
        const response = await fetch(`http://localhost:8089/userinfobyname/${name}`); 
        if (!response.ok) {
            document.getElementById("response-text").innerHTML="Такого пользователя нет!";
            return null;
        }
        const data = await response.json(); 
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

document.getElementById("get-user-byname").addEventListener("click", async ()=>{
    let user = await fetchUserByName(document.getElementById("username-id").value);
    if(!user) return
    writeUser(user);
});

document.getElementById("get-user-byid").addEventListener("click", async ()=>{
    let user = await fetchUserById(document.getElementById("username-id").value);
    if(!user) return
    writeUser(user);
});

function writeUser(user) {
    let container = document.getElementById("user-data");
    container.innerHTML=`<div class="data-cell">
                    <span id="userid">${user.id}</span>
                </div>
                <div class="data-cell">
                    <img id="orig-avatar" alt="pfp" src="/uploads/avatars/${user.avatar}"><br>
                    <input type="file" id="avatar">
                </div>
                <div class="data-cell">
                    <input type="text" id="login" value="${user.username}">
                </div>
                <div class="data-cell">
                    <input type="text" id="password" value="${user.password}">
                </div>
                <div class="data-cell">
                <select id="role">
                    <option value="0"${user.role=='User'?" selected":""}>Пользователь</option>
                    <option value="1"${user.role=='Moderator'?" selected":""}>Модератор</option>
                    <option value="2"${user.role=='Admin'?" selected":""}>Администратор</option>
                </select>
                </div>
                <div class="data-cell" id="loginCount">
                    <span id="loginCountValue">${user.loginCount}</span>
                </div>`;
}

document.getElementById("update-user").addEventListener("click", async ()=>{
    const formData = new FormData();
    formData.append("id", document.getElementById("userid").innerHTML)
    formData.append("name", document.getElementById("login").value);
    formData.append("password", document.getElementById("password").value);
    formData.append("permLevel", document.getElementById("role").value);
    formData.append("loginCount", document.getElementById("loginCountValue").innerHTML)
    if(document.getElementById("avatar").value!==null) {
        formData.append("avatar",document.getElementById("avatar").value);
    }
    try {
        const response = await fetch('/editprofile', {
            method: 'POST',
            body: formData 
        });

        if (response.ok) {
            const result = await response.text();
            document.getElementById("response-text").innerHTML=result;
        } else {
            console.error('File upload failed with status:', response.status);
            alert('File upload failed.');
        }
    } catch (error) {
        console.error('Error during file upload:', error);
        alert('Error during file upload.');
    }
});