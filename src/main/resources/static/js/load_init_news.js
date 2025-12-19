async function fetchNewsByPrev(id_prev) {
    try {
        const response = await fetch(`http://localhost:8089/prevnewsinfo/${id_prev}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

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

async function buildNews(news) {
    let author = await fetchUserById(news.author);
    let time = new Date(1000*news.publishTime);
    let imgHTML = "";
    console.log(news);
    for (let pic of news.pictures){
        imgHTML+=`<img th:src="@{${picPath}}" alt="pic">`;
    }
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

let j=-1;
let flag=false;
for (let i=0; i<5; i++) {
    fetchNewsByPrev(j).then(news => {
        if(news === null) {
            document.getElementById("loadmore-btn").disabled=true;
            flag=true;
        } else {
            j = buildNews(news);
        }
    });
    if (flag) break;
}
document.getElementById("loadmore-btn").addEventListener("click", async ()=>{
    for (let i=0; i<5; i++) {
        let news = await fetchNewsByPrev(j);
        if(news === null) {
            document.getElementById("loadmore-btn").disabled=true;
            break;
        }
        j = await buildNews(news);
    }
});