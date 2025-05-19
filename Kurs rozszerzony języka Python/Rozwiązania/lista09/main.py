# Generowanie wykresow walut dla api Narodowego Banku Polskiego
# Brak przewidywania przyszlych cen
# Wykres GBP i USD 2012-2013

from datetime import datetime, timedelta
import requests
import matplotlib.pyplot as plt

# Generator dat: (poczatek miesiaca, koniec miesiaca)
def get_month_start_end(year):
    month_ranges = []
    for month in range(1, 13):
        start_date = datetime(year, month, 1)
        if month == 12:
            end_date = datetime(year+1, 1, 1) - timedelta(days=1)
        else:
            end_date = datetime(year, month + 1, 1) - timedelta(days=1)
        month_ranges.append((start_date.strftime("%Y-%m-%d"), end_date.strftime("%Y-%m-%d")))
    return month_ranges

# Generator api
# api + {startDate}/{endDate}
def generate_json(months, api):
    data = []
    for i in range(12):
        if api[-1] != '/':
            api = api + '/'
        url = api + months[i][0] + '/' + months[i][1]
        res = requests.get(url)
        data.append(res.json())
        print(data)
    return data
        
# Generator listy srednich cen dla danego roku
# Dane podane sa jako lista slownikow typu:
# {'table': 'A', 'currency': 'dolar amerykański', 'code': 'USD', 'rates': [{'no': '213/A/NBP/2014', 'effectiveDate': '2014-11-03', 'mid': 3.3772}
def avg_prizes(data, pattern='mid'):
    avg_values = []
    for i in range(12):
        # Lista wartosci: data['rates'][i]['mid']
        values = [rate[pattern] for rate in data[i]['rates']]
        avg_values.append(sum(values) / len(values))
    return avg_values

# Rysowanie wykresu 
def draw_graph(api, years, name):
    months_names = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    for i in range(len(years)):
        months = get_month_start_end(years[i])
        data = generate_json(months, api)
        prizes = avg_prizes(data)
        plt.plot(months_names, prizes, marker='o', label=name+str(years[i]))



# TESTY
years = [2013, 2014]
api = ["http://api.nbp.pl/api/exchangerates/rates/a/gbp", "http://api.nbp.pl/api/exchangerates/rates/a/usd"]

plt.figure(figsize=(10, 6))
plt.grid(True)
plt.title('Average Prices Over Months')
plt.ylabel('Average Price (PLN)')

# GBP
draw_graph(api[0], years, name='GBP')

# USD
draw_graph(api[1], years, name='USD')
plt.legend()
plt.show()