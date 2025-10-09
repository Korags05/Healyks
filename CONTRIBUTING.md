# Contributing to Healyks

Thank you for your interest in contributing to Healyks! We're building an AI-powered healthcare assistant to serve underserved communities, and your contributions can make a real difference.

## Table of Contents
- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [How to Contribute](#how-to-contribute)
- [Development Setup](#development-setup)
- [Coding Standards](#coding-standards)
- [Pull Request Process](#pull-request-process)
- [Reporting Bugs](#reporting-bugs)
- [Suggesting Features](#suggesting-features)
- [Community](#community)

## Code of Conduct

By participating in this project, you agree to maintain a respectful, inclusive, and harassment-free environment for everyone. We're committed to:

- Using welcoming and inclusive language
- Being respectful of differing viewpoints and experiences
- Gracefully accepting constructive criticism
- Focusing on what's best for the community
- Showing empathy towards other community members

## Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/YOUR-USERNAME/Healyks.git
   cd Healyks
   ```
3. **Add the upstream repository**:
   ```bash
   git remote add upstream https://github.com/Korags05/Healyks.git
   ```
4. **Create a branch** for your changes:
   ```bash
   git checkout -b feature/your-feature-name
   ```

## How to Contribute

### Areas We Need Help With

- **Frontend Development**: UI/UX improvements, new features in Jetpack Compose
- **Documentation**: README improvements, code comments, user guides
- **Testing**: Writing unit tests, integration tests, UI tests
- **Localization**: Adding support for regional languages
- **Accessibility**: Making the app more accessible to users with disabilities
- **Bug Fixes**: Identifying and fixing issues

## Development Setup

### Prerequisites
- Android Studio (latest stable version)
- JDK 11 or higher
- Git
- Firebase account
- Google Cloud account (for Gemini API access)

### Environment Setup

1. **Install Android Studio** and required SDKs
2. **Configure Firebase**:
   - Create a Firebase project
   - Download `google-services.json`
   - Place it in the `app/` directory
   - Enable Firebase Authentication

3. **Set up local.properties**:
   ```properties
   BASE_URL="http://ec2-13-232-188-167.ap-south-1.compute.amazonaws.com:5000/"
   FIREBASE_TOKEN="your_firebase_server_key"
   ```

4. **Install dependencies**:
   ```bash
   ./gradlew build
   ```

5. **Run the app**:
   - Open the project in Android Studio
   - Sync Gradle files
   - Run on an emulator or physical device

## Coding Standards

### Android/Kotlin
- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use Jetpack Compose best practices
- Write meaningful variable and function names
- Keep functions small and focused (single responsibility principle)
- Add comments for complex logic

### Code Style
```kotlin
// Good
fun analyzeSymptoms(symptoms: List<String>): HealthReport {
    return symptomAnalyzer.analyze(symptoms)
}

// Bad
fun a(s: List<String>): HealthReport {
    return symptomAnalyzer.analyze(s)
}
```

### Backend/JavaScript
- Follow [Airbnb JavaScript Style Guide](https://github.com/airbnb/javascript)
- Use async/await for asynchronous operations
- Implement proper error handling
- Write clean, modular code

### Commit Messages
Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
feat: add medication reminder notification sound
fix: resolve crash on period tracker calendar view
docs: update API endpoint documentation
style: format symptom checker layout
refactor: optimize database queries
test: add unit tests for health education module
chore: update dependencies
```

## Pull Request Process

1. **Ensure your code builds** without errors or warnings
2. **Update documentation** if you've changed functionality
3. **Add tests** for new features
4. **Update the README.md** if needed
5. **Commit your changes** with clear, descriptive messages
6. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```
7. **Open a Pull Request** with:
   - Clear title describing the change
   - Detailed description of what and why
   - Screenshots/videos for UI changes
   - Reference to related issues (if any)

### PR Template
```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Performance improvement
- [ ] Code refactoring

## Testing
- [ ] Tested on Android emulator
- [ ] Tested on physical device
- [ ] Added unit tests
- [ ] All existing tests pass

## Screenshots (if applicable)
Add screenshots here

## Related Issues
Fixes #(issue number)
```

### Review Process
- At least one maintainer will review your PR
- Address any requested changes
- Once approved, a maintainer will merge your PR

## Reporting Bugs

### Before Submitting a Bug Report
- Check the [issue tracker](https://github.com/Korags05/Healyks/issues) for existing reports
- Verify you're using the latest version
- Collect relevant information (device, Android version, logs)

### Bug Report Template
```markdown
**Describe the bug**
A clear description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Go to '...'
2. Click on '...'
3. Scroll down to '...'
4. See error

**Expected behavior**
What you expected to happen.

**Screenshots**
If applicable, add screenshots.

**Device Information:**
- Device: [e.g. Samsung Galaxy S21]
- Android Version: [e.g. 12]
- App Version: [e.g. 1.0.0]

**Additional context**
Any other relevant information.
```

## Suggesting Features

We welcome feature suggestions! Please:

1. **Check existing issues** to avoid duplicates
2. **Open a new issue** with the "Feature Request" label
3. **Describe the feature** clearly:
   - What problem does it solve?
   - Who would benefit from it?
   - How should it work?
4. **Be open to discussion** and feedback

### Feature Request Template
```markdown
**Is your feature request related to a problem?**
A clear description of the problem.

**Describe the solution you'd like**
How you envision the feature working.

**Describe alternatives you've considered**
Other approaches you've thought about.

**Additional context**
Screenshots, mockups, or examples from other apps.
```

## Community

### Communication Channels
- **GitHub Issues**: For bug reports and feature requests
- **GitHub Discussions**: For questions and general discussions
- **Email**: kunalsaha1107@gmail.com for direct contact

### Recognition
Contributors will be acknowledged in:
- README.md Contributors section
- Release notes
- Project documentation

## Questions?

Don't hesitate to ask questions! You can:
- Open an issue with the "question" label
- Reach out via email
- Comment on existing issues or PRs

## License

By contributing to Healyks, you agree that your contributions will be licensed under the same license as the project.

---

Thank you for contributing to Healyks and helping us improve healthcare access in underserved communities! 🌍💙
