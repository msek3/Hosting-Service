$('.fileNotFound').hide();
let dropArea = document.getElementById('drop-area');
['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
    dropArea.addEventListener(eventName, preventDefaults, false)
});

function preventDefaults (e) {
    e.preventDefault();
    e.stopPropagation();
}

['dragenter', 'dragover'].forEach(eventName => {
    dropArea.addEventListener(eventName, highlight, false)
});

['dragleave', 'drop'].forEach(eventName => {
    dropArea.addEventListener(eventName, unhighlight, false)
});

function highlight(e) {
    dropArea.classList.add('highlight')
}

function unhighlight(e) {
    dropArea.classList.remove('highlight')
}

dropArea.addEventListener('drop', handleDrop, false);

function handleDrop(e) {
    let dt = e.dataTransfer;
    let files = dt.files;

    handleFiles(files)
}

function handleFiles(files) {
    ([...files]).forEach(uploadFile);
}

function uploadFile(uploadFile) {
    let objFormData = new FormData();
    objFormData.append('file', uploadFile);
    fetch("/", {
        method: 'POST',
        body: objFormData,
        processData: false
    })
        .then((e) => {
            dropArea.parentNode.removeChild(dropArea);
            e.body
                .getReader()
                .read()
                .then(((e) => createLink(parseArray(e.value))));
        })
        .catch((e1) => { alert(e1);})
}

function createLink(id) {
    const form1 = document.getElementsByClassName("downloadLink");
    $(form1).append('<div class="input-group" style="width: 40%; margin:auto; margin-top: 50px">' +
        '<input type="text" class="form-control" value="http://localhost:8080/' + id + '" style="margin-right: 10px;">' +
        '<button type="button" class="btn btn-secondary" onclick=download(' + id + ')>Download</button>' +
        '</div>'
    );
}

function download(id) {
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/" + id,
        success: function (data) {

        },
        error(data){
            if(data.status === 200){
                window.location.href="http://localhost:8080/" + id;
            } else {
                $('.fileNotFound').show();
            }
        }
    });

}

function parseArray(array) {
    let val = "";
    array.forEach( v => val += String.fromCharCode(v));
    return val;
}
