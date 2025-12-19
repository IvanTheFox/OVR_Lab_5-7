async function fetchUserById(id) {
    try {
        const response = await fetch(`http://localhost:8089/userinfobyid/?id=${id}`); 
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); 
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function fetchUserByName(name) {
    try {
        const response = await fetch(`http://localhost:8089/userinfobyname/?name=${name}`); 
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); 
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function fetchNewsByPrev(id_prev) {
    try {
        const response = await fetch(`http://localhost:8089/prevnewsinfo/?id=${id_prev}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); 
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function fetchNewsById(id) {
    try {
        const response = await fetch(`http://localhost:8089/newsinfo/?id=${id}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); 
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function buildNews(news) {
    let author = fetchUserByName(news.author);
    let time = new Date(1000*news.publishTime);
    let imgHTML = "";
    news.pictures.forEach(picPath => {
        imgHTML+=`<img th:src="@{${picPath}}" alt="pic">`
    });
    temp3.innerHTML=`<div class="news-creator">
                    <img alt="pfp" src="">
                    <div id="desc-divider">
                        <div class="new-description">
                            <b>${author.getName()}</b>
                            <p>${time.toLocaleString()}</p>`+
                            '<p id="news-id" th:if="${user.permLevel>0}">'+`${news.getId()}</p>+`
                        `</div>
                        <div><button id="delete-news">X</button></div>
                    </div>
                </div>
                <div class="article-content">
                    <h2>${news.title}</h2>
                    <p>${news.text}</p>`
                    +imgHTML+
                `</div>`;
    document.getElementById("newscolumn").appendChild(temp3);
    return news.id;
}

async function buildNewsEditor(news){
    let article = document.getElementsByClassName("article-content");
    let imagesHTML="";

    news.pictures.forEach(picPath => {
        imagesHTML+='<div class="news-image">'+
        `<img alt="fto" th:src="@{${picPath}}"><br>`+
        '<button class="del-image">X</button>'+
        '</div>';
    })
    article.innerHTML=`<h2><input id="article-title" required>${news.title}</h2>
        <textarea id="article-text" rows="15" maxlength="3000" required>${news.text}</textarea><br>
        ${imagesHTML}
        <input type="file" name="files" id="article-images" multiple required>`;
}