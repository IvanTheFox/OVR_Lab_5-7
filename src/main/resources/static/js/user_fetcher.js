export async function fetchUserById(id) {
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

export async function fetchUserByName(name) {
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