console.log("Audio Client script loaded.");

// Elements
const dashboardElement = document.getElementById("dashboard");
const audioElement = document.getElementById("audio");
const currentTitleElement = document.getElementById("current-title");

// Events
function loadMusicDirectory() {
    fetch("/api/dashboard/load/music")
        .then(res => res.json())
        .then(res => {
            dashboardElement.innerHTML = "";
            res.audioList.forEach(e => {
                const div = document.createElement("div");
                div.className = "item";
                div.textContent = e.name;
                div.onclick = () => loadAudioToPlayer(div, e);
                dashboardElement.append(div);
            });
        });
}

function loadAudioToPlayer(element, e) {
    audioElement.src = `/api/dashboard/load?path=${encodeURIComponent(e.path)}`;
    currentTitleElement.textContent = e.name;
    element.classList.add("active");
    audioElement.play();
}
