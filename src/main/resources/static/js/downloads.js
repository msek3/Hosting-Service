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

function createChart() {
    const apiUrl = "http://localhost:8080";
    const $list = $('.file-list');

    $.ajax({
        url : apiUrl + '/downloads/files7days',
        dataType : 'json'
    })
        .done((res) => {
            let data = [];
            let dates = [];
            let summ = 0;
            $list.empty();
            res.forEach(el => {
                data.push(el);
                summ += el;
            })
            var d = new Date();
            for(let i = 0; i < 7; i++) {
                d.setDate(d.getDate()-1);
                var month = d.getMonth() + 1;
                var day = d.getDate();
                if (month.toString().length == 1) month = "0" + month.toString();
                if (day.toString().length == 1) day = "0" + day.toString();
                dates.push(day+"."+month);
            }

            document.getElementById('avgValue').innerHTML = "Średnia ilośc pobrań w ciągu dnia wynosi: " + (summ/7).toFixed(2);
            document.getElementById('summValue').innerHTML = "Łączna suma pobrań w ciągu tygodnia wynosi: " + summ;

            var ctx = document.getElementById('myChart');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: dates,
                    datasets: [{
                        label: '# liczba pobrań danego dnia',
                        data: data,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.5)',
                            'rgba(54, 162, 235, 0.5)',
                            'rgba(255, 206, 86, 0.5)',
                            'rgba(75, 192, 192, 0.5)',
                            'rgba(153, 102, 255, 0.5)',
                            'rgba(221, 126, 64, 0.5)',
                            'rgba(113, 237, 101, 0.5)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)',
                            'rgba(113, 237, 101, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            });
        });


}

getfiles();

createChart();