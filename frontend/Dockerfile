
# These steps are separate so Docker can reuse the dependency-installation cache when only your React source code changes.
# Copy package files
# Install dependencies from package-lock.json

# ================================
# BUILD STAGE
# ================================

# Use Node.js to build the React application
FROM node:24-alpine AS build

# Set the working directory
WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies from package-lock.json
RUN npm ci

# Copy all frontend project files
COPY . .

# Build the production files into the dist folder
RUN npm run build


# ================================
# RUNTIME STAGE
# ================================

# After npm run build, React generates static files in the dist/ folder, including HTML, CSS, JavaScript, and images.
# NGINX serves these static files to users through their browsers on port 80.
# NGINX is a lightweight production server that runs the frontend efficiently without needing the Vite development server.

# Use NGINX to serve the production frontend
FROM nginx:alpine

# Copy the built files from the build stage to NGINX
COPY --from=build /app/dist /usr/share/nginx/html

# Copy custom NGINX configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Document the frontend port
EXPOSE 80

# Start the NGINX server in the foreground
CMD ["nginx", "-g", "daemon off;"]
