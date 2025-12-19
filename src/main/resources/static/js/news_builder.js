import * as ufetch from "./user_fetcher"

export async function fetchNewsByPrev(id_prev) {
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

export async function fetchNewsById(id) {
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

export async function buildNews(news) {
    let author = ufetch.fetchUser(news.author);
    let temp = document.createElement("div");
    temp.setAttribute("class", "news-creator");
    let temp2 = document.createElement("img");
    temp2.setAttribute("alt", "pfp");
    temp2.setAttribute("src", author.avatar);
    temp.appendChild(temp2);
    temp2 = document.createElement("div");
    temp2.setAttribute("class","new-description");
    let temp3 =document.createElement("b");
    temp3.innerHTML=author.name;
    temp2.appendChild(temp3);
    temp3 =document.createElement("p");
    temp3.innerHTML = new Date(1000*news.publishTime);
    temp2.appendChild(temp3);
    temp.appendChild(temp2);
    temp3 = document.createElement("div");
    temp3.setAttribute("class", "article");
    temp3.appendChild(temp);
    temp = document.createElement("div");
    temp.setAttribute("class", "article-content");
    temp2 = document.createElement("h2");
    temp2.innerHTML = news.title;
    temp.appendChild(temp2);
    temp2 = document.createElement("p");
    temp2.innerHTML = news.text;
    temp.appendChild(temp2);
    temp2 = document.createElement("p");
    temp2.setAttribute("th:if", "${user.role.permLevel>0}");
    temp2.id="news-id";
    temp2.innerHTML = `ID=${news.id}`;
    temp.appendChild(temp2);
    news.pictures.forEach(picPath => {
        temp2 = document.createElement("img");
        temp2.setAttribute("src", picPath);
        temp.appendChild(temp2);
    });
    temp3.appendChild(temp);
    document.getElementById("newscolumn").appendChild(temp3);
    return news.id;
}

export async function buildNewsEditor(news){
    let article = document.getElementsByClassName("article-content");
    let imagesHTML=""

    news.pictures.forEach(picPath => {
        imagesHTML+='<div class="news-image">'+
        `<img alt="fto" value=${picPath}><br>`+
        '<button class="del-image">X</button>'+
        '</div>';
    })
    article.innerHTML=`<h2><input id="article-title" required>${news.title}</h2>
        <textarea id="article-text" rows="15" maxlength="3000" required>${news.text}</textarea><br>
        ${imagesHTML}
        <button id="add-image">Добавить картинку</button>`;
}