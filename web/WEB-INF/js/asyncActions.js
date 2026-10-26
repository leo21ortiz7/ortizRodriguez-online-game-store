/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("submit", async function (event) {
    const form = event.target;

    if (!form.matches(".async-action-form")) {
        return;
    }

    event.preventDefault();

    const message = form.querySelector(".action-message");
    const button = form.querySelector('button[type="submit"]');

    try {
        button.disabled = true;
        message.textContent = "Processing...";

        const response = await fetch(form.action, {
            method: "POST",
            body: new URLSearchParams(new FormData(form))
        });

        const result = await response.text();

        if (!response.ok) {
            throw new Error(result || "The operation failed.");
        }

        message.textContent = result;

    } catch (error) {
        message.textContent =
            error.message || "An unexpected error occurred.";
    } finally {
        button.disabled = false;
    }
});