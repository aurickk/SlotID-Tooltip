<p align="center">
<img src="https://github.com/user-attachments/assets/9a85100c-d336-4705-b7be-a9458971e3c0" alt="SlotID Tooltip Banner" width="25%"/>
</p>
<h1 align="center">SlotID Tooltip</h1>

<p align="center">
A simple client-side Fabric mod that displays the slot ID in tooltips when hovering over inventory slots.
</p>

## Requirements

- Minecraft 1.21.1 - 1.21.10

- Fabric Loader 0.15.0 or higher

- Java 21 or higher

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for your Minecraft version


2. Download the latest `slotid-tooltip-[version]-[minecraft_version].jar` from the [Releases](https://github.com/aurickk/SlotID-Tooltip/releases/) page


3. Place the mod in your `.minecraft/mods` folder


4. Launch Minecraft

## Usage

Simply hover over any inventory slot (empty or containing an item) to see its slot ID displayed in the tooltip. 

<img width="523" src="https://github.com/user-attachments/assets/78f37634-254f-41cd-a964-1b5ce63515c9" />

This is particularly useful in servers with custom GUI textures where its difficult to indentify individual slots.

<img width="523" src="https://github.com/user-attachments/assets/a93db4b4-aaf6-4ff0-a96a-089ebf284796" />

Also works in the Creative mode inventory menu.

<img width="523" alt="image" src="https://github.com/user-attachments/assets/4d8ab76f-abb1-428b-9ccd-05dc3509c90c" />





## Building from Source

### Prerequisites

- **Java 21** or higher
- **Gradle** (included via wrapper)

### Building the Minecraft Mod

1. **Clone the repository**
   ```bash
   git clone https://github.com/aurickk/SlotID-Tooltip.git
   cd SlotID-Tooltip/
   ```

2. **Build the mod**
   ```bash
   # Windows
   .\gradlew.bat build
   
   # Linux/Mac
   ./gradlew build
   ```

3. **Find the built mod**
   - The mod JAR will be in `build/libs/slotid-tooltip-*.jar`






