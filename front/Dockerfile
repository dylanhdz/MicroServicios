FROM node:18 AS build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build /app/dist/crud-msvc/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf