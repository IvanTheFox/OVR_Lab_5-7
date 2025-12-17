async function fetchGreeting() {
    try {
        const response = await fetch('http://localhost:8080/userinfo/1'); 
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); 
        if(data.role)
        console.log(data.message); // Output: Hello from Spring Boot!
        // You can now use this data to update your HTML/UI
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}
