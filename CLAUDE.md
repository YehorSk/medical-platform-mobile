# Medical Platform Mobile

Kotlin Multiplatform + Compose Multiplatform client for a medical platform (patients & doctors).
Targets: Android (`androidApp`) and Desktop/JVM (`desktopApp`). There is no iOS app yet,
even though the README mentions one. Almost all code lives in `shared`.

## Build & run
- Android: `./gradlew :androidApp:assembleDebug`
- Desktop: `./gradlew :desktopApp:run --no-configuration-cache`
  (always pass `--no-configuration-cache`; the run fails without it)
- Versions live in `gradle/libs.versions.toml`. Add dependencies there, never inline.

## Stack
Compose MPP, Material3 (+ adaptive), Koin, Ktor (HTTP + WebSockets), kotlinx.serialization,
kotlinx.datetime, DataStore, Paging, Kermit logging, FileKit, Firebase (Android only), Kizitonwose calendar.

## Architecture (`shared/src/commonMain/.../medical_platform_mobile`)
- `core/`: shared data (network, datastore, mappers), domain (models, repos, services), UI components, utils
- `feature/<name>/{data,domain,presentation,di}`: appointments, auth, chat, connections, dashboard, medical, settings
- `navigation/`: `NavigationRoot`, with separate `AuthNavGraph`, `PatientNavGraph`, `DoctorNavGraph`
- Platform-specific code goes in `androidMain` / `desktopMain` / `jvmMain` via expect/actual

## Conventions
- Screens use MVI: `XxxViewModel`, `XxxState`, `XxxAction`, optional `XxxEvent`, in `presentation/<screen>/viewmodel/`.
  Collect one-off events with `ObserveAsEvents`.
- Data layer: DTOs in `data/dto`, mapped to domain models in `data/mappers`. Domain stays free of DTOs and Ktor.
- Errors: return `Result<T, DataError>` (`core/util/Result.kt`), not exceptions. Convert to UI text with `ErrorToUiText`/`UiText`.
- Register each feature's dependencies in its own `di/XxxModule.kt`.
- Patient and doctor roles get separate nav graphs. Check which role a screen belongs to.
- Strings and drawables go in `composeResources`.

## Shared UI components (`core/ui/components`)
Reusable composables, grouped by type:
- `buttons/`: `DefaultButton`, `DefaultAssistChip`, `TabToggleButton`
- `cards/`: `DefaultContentCard`, `DefaultInfoCard`, `DefaultListCard`
- `dialogs/`: `DestructiveConfirmationDialog`
- `layouts/`: `MainScaffold`, `AppTopBar`, `DashboardTopBar`, `BottomBar`
- `textfields/`: `DefaultTextField`, `PwdTextField`, `DefaultMultilineTextField`, `DefaultDropdownTextField`,
  `DefaultDatePicker`, `DefaultTimePicker`, `DefaultStepperField`
- `other/`: `AutoResizedText`, `NoConnectionBanner`

Rules:
- Check here before writing a new composable, and use the existing one if it fits.
- If a UI pattern shows up in two or more screens, move it here as a parameterized component,
  in the matching type folder, and switch the screens over to it. Create a new folder only if no existing type fits.
- Name new shared components with the `Default…` prefix, following the existing ones.
- Composables used by only one screen stay in that screen's `presentation/<screen>/components/`.

## Backend
- Base URLs are in `core/data/network/UrlConstants.kt` (HTTP `/api` + WebSocket for chat).
- JWT auth with refresh (`TokenRefresher`, `JwtUtils`). Auth failures go through `AuthEventManager`.

## Security
This is a medical app that handles sensitive health and personal data. Treat security as a requirement, not an extra.
- Persist session data and tokens only through `SessionDataStore`, which encrypts with `Crypto`
  (expect/actual in `core/data/datastore`). Never write tokens, user data or medical data to plain
  DataStore, files or preferences.
- Never store the local PIN in plain text. Use `PinHasher` (salted hash + constant-time `equals`) through `PinStorage`/`PinDataStore`.
- Platform-specific crypto belongs in `Crypto.android.kt` / `Crypto.desktop.kt`. Don't reimplement it in feature code.
- All authenticated requests go through the shared Ktor client from `HttpClientFactory` (token attach + `TokenRefresher`).
  Don't create ad-hoc `HttpClient`s or attach tokens by hand.
- On logout or auth failure (`AuthEventManager`), clear the session and any cached user data.
- Validate user input before sending it. Never trust IDs or roles from the UI to gate access.
  Patient and doctor screens must stay in their own nav graphs.
- Don't add new dependencies, permissions or exported Android components without a clear need.
- When you notice a security weakness while working, point it out, even if it's outside the current task.

### Allowed for now (dev phase), fix before release
- Debug logging of tokens/data is intentional. Don't flag it.
- `usesCleartextTraffic="true"` and plain `http://`/`ws://` in `UrlConstants` (local backend).
- `allowBackup="true"` in `AndroidManifest.xml`.
