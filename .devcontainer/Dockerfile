# Use an official Maven image which includes a specific version of OpenJDK.
# Using a specific tag (e.g., 3.9.9-eclipse-temurin-21) is recommended for reproducibility.
FROM maven:3.9.9-eclipse-temurin-21

# Create a non-root user to avoid running everything as root for security reasons.
# The user is named 'vscode' and given a home directory.
ARG USERNAME=vscode
ARG USER_UID=1100
ARG USER_GID=$USER_UID

RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME

USER $USERNAME