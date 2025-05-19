# Asynchroniczne pobieranie danych bez API Keys
import asyncio
import aiohttp

async def fetch(session, url):
    async with session.get(url) as response:
        return await response.text()

urls = ["https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m", "http://api.nbp.pl/api/cenyzlota/2013-01-01/2013-01-31/"]

async def main():
    async with aiohttp.ClientSession() as session:
        weather_data, nbp_data = await asyncio.gather(
            fetch(session,  urls[0]),
            fetch(session, urls[1])
        )

        print("uwr",  weather_data)
        print("nbp", nbp_data)

asyncio.run(main())
