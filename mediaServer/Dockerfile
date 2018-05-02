FROM golang:latest

RUN mkdir /usr/local/go/src/mediaServer

ADD . /usr/local/go/src/mediaServer

WORKDIR /usr/local/go/src/mediaServer

EXPOSE 5001

RUN go build -o main .

CMD ["/usr/local/go/src/mediaServer/main"]