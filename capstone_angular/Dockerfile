#Step 1: Build stage
FROM node:16 as builder
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

#Step 2: Package stage
FROM nginx:alpine
COPY --from=builder /app/dist/capstone_angular /usr/share/nginx/hmtl
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
