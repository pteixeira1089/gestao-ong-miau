# --- Estágio 1: Build (O Construtor) ---
FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

# [IMPORTANTE] Declaramos que aceitamos esse argumento do Railway (ou docker build --build-arg)
ARG VITE_API_URL

# [IMPORTANTE] Transformamos o argumento em variável de ambiente para o comando de build
ENV VITE_API_URL=$VITE_API_URL

# Agora o build vai ler a variável e "chumbar" no JS gerado na pasta dist
RUN npm run build

# --- Estágio 2: Nginx (O Entregador) ---
FROM nginx:alpine

# Copia a configuração do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copia apenas os arquivos prontos da pasta dist gerada no estágio anterior
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
