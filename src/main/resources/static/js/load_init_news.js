async function fetchNewsByPrev(id_prev) {
    try {
        const response = await fetch(`http://localhost:8089/prevnewsinfo/${id_prev}`);
        if (!response.ok) {
            return null;
        }
        const data = await response.json();
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function fetchUserById(id) {
    try {
        const response = await fetch(`http://localhost:8089/userinfobyid/${id}`); 
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); 
        return data
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

async function deleteNews(id) {
    try {
        const response = await fetch(`http://localhost:8089/deletenews/${id}`, {
            method: 'POST'
        });
    } catch (error) {
        console.error('Error during news upload:', error);
        alert('Error during news upload.');
    }
}

async function buildNews(news) {
    let author = await fetchUserById(news.author);
    let time = new Date(1000*news.publishTime);
    let imgHTML = "";
    let temp3 = document.createElement("div");
    temp3.setAttribute("class","article");
    for (let pic of news.pictures){
        imgHTML+=`<img src="uploads/news_pictures/${pic}" alt="pic">`;
    }
    temp3.innerHTML=`<div class="news-creator">
                    <img alt="pfp" src="/uploads/avatars/${author.avatar}">
                    <div id="desc-divider">
                        <div class="new-description">
                            <b>${author.username}</b>
                            <p>${time.toLocaleString()}</p>`+
                            '<p class="news-id" if="${user.permLevel>0}">'+`ID новости=${news.id}</p>`+
                            `<span name="id" class="hidden-id" hidden>${news.id}</span>`+
                        `</div>
                        <div><button type="submit" class="delete-news">Удалить новость</button></div>
                    </div>
                </div>
                <div class="article-content">
                    <h2>${news.title}</h2>
                    <p>${news.text}</p>`
                    +imgHTML+
                `</div>`;
    document.getElementById("news-container").appendChild(temp3);
    return news.id;
}

async function showNews() {
    let j=-1;
    let flag=false;
    for (let i=0; i<5; i++) {
        const result = await fetchNewsByPrev(j);
        if (result === null) {
            document.getElementById("loadmore-btn").disabled = true;
            flag = true;
        } else {
            j = await buildNews(result);
        }
        if (flag) break;
    }
    document.getElementById("loadmore-btn").addEventListener("click", async ()=>{
        flag=false;
        for (let i=0; i<5; i++) {
            const result = await fetchNewsByPrev(j);
            if (result === null) {
                document.getElementById("loadmore-btn").disabled = true;
                flag = true;
            } else {
                j = await buildNews(result);
            }
            if (flag) break;
        }
    });
    
    for (let btn of document.getElementsByClassName("delete-news")) {
        btn.addEventListener("click",event => {
            console.log(event);
            console.log(event.currentTarget);
            let id = event.currentTarget.parentElement.previousElementSibling.getElementsByClassName("hidden-id")[0].innerHTML;
            deleteNews(id);
            event.currentTarget.parentElement.parentElement.parentElement.parentElement.remove();
        })
    }
}
