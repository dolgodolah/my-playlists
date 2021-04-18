const clock = "";

function handleDate() {
    const date = new Date();
    const hour = date.getHours()
    const minute = date.getMinutes();
    const day = date.getDay();
    console.log(hour, minute, day, date.getDate());
}

function init() {
    handleDate();
}


init();