async function fetchNewsByPrev(num) {
    try {
        const response = await fetch(`http://localhost:8080/prevnewsinfo/${num}`);
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
        const response = await fetch(`http://localhost:8080/newsinfo/${id}`);
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
    let author = fetchUser(news.author);
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
    news.pictures.forEach(picPath => {
        temp2 = document.createElement("img");
        temp2.setAttribute("src", picPath);
        temp.appendChild(temp2);
    });
    temp3.appendChild(temp);
    document.getElementById("newscolumn").appendChild(temp3);
    return news.id;
}

for (let i=0, j=-1; i<5; i++) {
    let news = fetchNewsByPrev(j);
    j = buildNews(news);
}
document.getElementById("loadmore-btn").addEventListener("click", ()=>{
    for (let i=0, j=-1; i<5; i++) {
        let news = fetchNewsByPrev(j);
        j = buildNews(news);
    }
});