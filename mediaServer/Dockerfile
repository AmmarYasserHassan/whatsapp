FROM python:3

WORKDIR /mediaServer

COPY . .

RUN pip install -r requirements.txt

EXPOSE 5001

CMD ["python", "server.py"]