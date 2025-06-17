# Use an official Maven image which includes a specific version of OpenJDK.
# Using a specific tag (e.g., 3.8.5-openjdk-17) is recommended for reproducibility.
FROM maven:3.8.5-openjdk-17

# Create a non-root user to avoid running everything as root for security reasons.
# The user is named 'vscode' and given a home directory.
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME

# You can add OS-level dependencies here if needed.
# For example, to install Node.js for a full-stack application:
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive && apt-get install -y nodejs npm

# Set a working directory to keep things organized.
# The project files will be mounted here.
WORKDIR /workspace

# Switch to the non-root user
USER $USERNAME