function getfiles() {
    const apiUrl = "http://localhost:8080";
    const $list = $('.file-list');

    $.ajax({
        url : apiUrl + '/downloads/files',
        dataType : 'json'
    })
        .done((res) => {
            $list.empty();
            res.forEach(el => {
                $list.append(`<tr>
                    <td class="filenameInstance">${el.name}</td>
                    <td class="downloadsInstance">${el.downloads}</td>
                    </tr>`);
            })
        });
}

getfiles();