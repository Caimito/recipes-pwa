#!/bin/bash

# Create a new tmux session named "myapp" and detach from it
tmux new-session -d -s myapp

# Start docker-compose in the first window
tmux send-keys -t myapp:0 'docker-compose --profile dev up' C-m

# Create a new window for the backend and start the Maven build
tmux new-window -t myapp -n backend
tmux send-keys -t myapp:1 'cd backend && mvn spring-boot:run' C-m

# Create a new window for the webapp and start the Vue.js server
tmux new-window -t myapp -n webapp
tmux send-keys -t myapp:2 'cd webapp && npm run serve' C-m

# Create a new window with a shell available
tmux new-window -t myapp -n shell

# Attach to the tmux session
tmux attach-session -t myapp
