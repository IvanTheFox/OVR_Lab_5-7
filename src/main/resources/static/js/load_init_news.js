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

for (let i=0, j=-1; i<5; i++) {
    let news = builder.fetchNewsByPrev(j);
    j = builder.buildNews(news);
}
document.getElementById("loadmore-btn").addEventListener("click", ()=>{
    for (let i=0; i<5; i++) {
        let news = builder.fetchNewsByPrev(j);
        j = builder.buildNews(news);
    }
});