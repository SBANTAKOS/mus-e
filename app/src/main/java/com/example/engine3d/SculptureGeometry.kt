package com.example.engine3d

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.model.SculptureMaterial
import com.example.model.SculptureType
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

data class Vec3(val x: Float, val y: Float, val z: Float) {
    operator fun plus(o: Vec3) = Vec3(x + o.x, y + o.y, z + o.z)
    operator fun minus(o: Vec3) = Vec3(x - o.x, y - o.y, z - o.z)
    operator fun times(s: Float) = Vec3(x * s, y * s, z * s)

    fun length(): Float = sqrt(x * x + y * y + z * z)

    fun normalize(): Vec3 {
        val len = length()
        return if (len > 0.0001f) Vec3(x / len, y / len, z / len) else Vec3(0f, 1f, 0f)
    }

    fun dot(o: Vec3): Float = x * o.x + y * o.y + z * o.z

    fun cross(o: Vec3): Vec3 = Vec3(
        y * o.z - z * o.y,
        z * o.x - x * o.z,
        x * o.y - y * o.x
    )

    fun rotateY(angleRad: Float): Vec3 {
        val cosA = cos(angleRad)
        val sinA = sin(angleRad)
        return Vec3(
            x * cosA + z * sinA,
            y,
            -x * sinA + z * cosA
        )
    }

    fun rotateX(angleRad: Float): Vec3 {
        val cosA = cos(angleRad)
        val sinA = sin(angleRad)
        return Vec3(
            x,
            y * cosA - z * sinA,
            y * sinA + z * cosA
        )
    }
}

data class Face3D(
    val a: Int,
    val b: Int,
    val c: Int
)

data class ProjectedFace(
    val p1: Offset,
    val p2: Offset,
    val p3: Offset,
    val avgZ: Float,
    val color: Color,
    val wireframeColor: Color
)

data class Mesh3D(
    val name: String,
    val vertices: List<Vec3>,
    val faces: List<Face3D>
)

object SculptureGenerator {

    fun getMeshFor(type: SculptureType): Mesh3D {
        return when (type) {
            SculptureType.ISLAMIC_ASTROLABE -> createIslamicAstrolabeMesh()
            SculptureType.APOLLO_BUST -> createApolloBustMesh()
            SculptureType.VENUS_APHRODITE -> createVenusMesh()
            SculptureType.THE_THINKER -> createTheThinkerMesh()
            SculptureType.WINGED_VICTORY -> createWingedVictoryMesh()
            SculptureType.MOBIUS_CONTEMPORARY -> createMobiusStripMesh()
        }
    }

    // 0. Astronomical Astrolabe of Damascus & Andalusia:
    // Outer graduated limb ring, inner recessed tympan plate, pierced rete with star pointers,
    // ornate calligraphic throne (kursi), and suspension shackle (halqa).
    private fun createIslamicAstrolabeMesh(): Mesh3D {
        val verts = mutableListOf<Vec3>()
        val faces = mutableListOf<Face3D>()

        val numSegments = 32
        val outerRadius = 1.6f
        val innerRadius = 1.35f
        val tympanRadius = 1.30f
        val thickness = 0.12f

        // Front Rim (Outer & Inner)
        val frontRimStart = verts.size
        for (i in 0 until numSegments) {
            val a = i * 2f * PI.toFloat() / numSegments
            val cosA = cos(a)
            val sinA = sin(a)
            // Outer front
            verts.add(Vec3(cosA * outerRadius, sinA * outerRadius, thickness))
            // Inner front (rim border)
            verts.add(Vec3(cosA * innerRadius, sinA * innerRadius, thickness))
        }
        for (i in 0 until numSegments) {
            val next = (i + 1) % numSegments
            val v0 = frontRimStart + i * 2
            val v1 = frontRimStart + i * 2 + 1
            val v2 = frontRimStart + next * 2
            val v3 = frontRimStart + next * 2 + 1
            faces.add(Face3D(v0, v1, v2))
            faces.add(Face3D(v2, v1, v3))
        }

        // Back Rim & Plate
        val backRimStart = verts.size
        for (i in 0 until numSegments) {
            val a = i * 2f * PI.toFloat() / numSegments
            val cosA = cos(a)
            val sinA = sin(a)
            verts.add(Vec3(cosA * outerRadius, sinA * outerRadius, -thickness))
            verts.add(Vec3(0f, 0f, -thickness))
        }
        for (i in 0 until numSegments) {
            val next = (i + 1) % numSegments
            val v0 = backRimStart + i * 2
            val v1 = backRimStart + i * 2 + 1
            val v2 = backRimStart + next * 2
            faces.add(Face3D(v0, v2, v1))
        }

        // Outer Edge Cylindrical Side
        for (i in 0 until numSegments) {
            val next = (i + 1) % numSegments
            val vf0 = frontRimStart + i * 2
            val vf2 = frontRimStart + next * 2
            val vb0 = backRimStart + i * 2
            val vb2 = backRimStart + next * 2
            faces.add(Face3D(vf0, vf2, vb0))
            faces.add(Face3D(vf2, vb2, vb0))
        }

        // Recessed Front Tympan Plate (astronomical coordinates disc)
        val centerFrontIdx = verts.size
        verts.add(Vec3(0f, 0f, 0.02f))
        val tympanBorderStart = verts.size
        for (i in 0 until numSegments) {
            val a = i * 2f * PI.toFloat() / numSegments
            verts.add(Vec3(cos(a) * tympanRadius, sin(a) * tympanRadius, 0.02f))
        }
        for (i in 0 until numSegments) {
            val next = (i + 1) % numSegments
            val v1 = tympanBorderStart + i
            val v2 = tympanBorderStart + next
            faces.add(Face3D(centerFrontIdx, v1, v2))
        }

        // Ornate Throne (Kursi) at top
        val kursiBaseStart = verts.size
        verts.add(Vec3(-0.8f, 1.45f, 0.08f))  // 0
        verts.add(Vec3(0.8f, 1.45f, 0.08f))   // 1
        verts.add(Vec3(-0.4f, 1.95f, 0.08f))  // 2
        verts.add(Vec3(0.4f, 1.95f, 0.08f))   // 3
        verts.add(Vec3(0.0f, 2.20f, 0.08f))   // 4 (apex)
        verts.add(Vec3(-0.8f, 1.45f, -0.08f)) // 5
        verts.add(Vec3(0.8f, 1.45f, -0.08f))  // 6
        verts.add(Vec3(-0.4f, 1.95f, -0.08f)) // 7
        verts.add(Vec3(0.4f, 1.95f, -0.08f))  // 8
        verts.add(Vec3(0.0f, 2.20f, -0.08f))  // 9

        val kb = kursiBaseStart
        faces.add(Face3D(kb + 0, kb + 1, kb + 2))
        faces.add(Face3D(kb + 1, kb + 3, kb + 2))
        faces.add(Face3D(kb + 2, kb + 3, kb + 4))
        faces.add(Face3D(kb + 5, kb + 7, kb + 6))
        faces.add(Face3D(kb + 6, kb + 7, kb + 8))
        faces.add(Face3D(kb + 7, kb + 9, kb + 8))
        faces.add(Face3D(kb + 0, kb + 2, kb + 5))
        faces.add(Face3D(kb + 2, kb + 7, kb + 5))
        faces.add(Face3D(kb + 1, kb + 6, kb + 3))
        faces.add(Face3D(kb + 3, kb + 6, kb + 8))
        faces.add(Face3D(kb + 2, kb + 4, kb + 7))
        faces.add(Face3D(kb + 4, kb + 9, kb + 7))
        faces.add(Face3D(kb + 3, kb + 8, kb + 4))
        faces.add(Face3D(kb + 4, kb + 8, kb + 9))

        // Suspension Ring (Halqa) at the top of the Kursi
        val ringCenterY = 2.45f
        val ringOuter = 0.35f
        val ringInner = 0.22f
        val ringSegs = 16
        val ringStart = verts.size
        for (i in 0 until ringSegs) {
            val a = i * 2f * PI.toFloat() / ringSegs
            val c = cos(a)
            val s = sin(a)
            verts.add(Vec3(c * ringOuter, ringCenterY + s * ringOuter, 0.05f))
            verts.add(Vec3(c * ringInner, ringCenterY + s * ringInner, 0.05f))
        }
        for (i in 0 until ringSegs) {
            val next = (i + 1) % ringSegs
            val v0 = ringStart + i * 2
            val v1 = ringStart + i * 2 + 1
            val v2 = ringStart + next * 2
            val v3 = ringStart + next * 2 + 1
            faces.add(Face3D(v0, v1, v2))
            faces.add(Face3D(v2, v1, v3))
        }

        // The Pierced Rete (Al-Ankabut):
        val eclipticCenter = Vec3(0f, -0.35f, 0.06f)
        val eclipticRadius = 0.85f
        val eclipticSegs = 20
        val eclipticStart = verts.size
        for (i in 0 until eclipticSegs) {
            val a = i * 2f * PI.toFloat() / eclipticSegs
            val c = cos(a)
            val s = sin(a)
            verts.add(Vec3(eclipticCenter.x + c * (eclipticRadius + 0.06f), eclipticCenter.y + s * (eclipticRadius + 0.06f), 0.07f))
            verts.add(Vec3(eclipticCenter.x + c * (eclipticRadius - 0.06f), eclipticCenter.y + s * (eclipticRadius - 0.06f), 0.07f))
        }
        for (i in 0 until eclipticSegs) {
            val next = (i + 1) % eclipticSegs
            val v0 = eclipticStart + i * 2
            val v1 = eclipticStart + i * 2 + 1
            val v2 = eclipticStart + next * 2
            val v3 = eclipticStart + next * 2 + 1
            faces.add(Face3D(v0, v1, v2))
            faces.add(Face3D(v2, v1, v3))
        }

        // Pointed Flame Star Pointers
        val numPointers = 8
        for (p in 0 until numPointers) {
            val ang = p * 2f * PI.toFloat() / numPointers + 0.25f
            val baseCos = cos(ang)
            val baseSin = sin(ang)
            val perpCos = -sin(ang)
            val perpSin = cos(ang)

            val pStart = verts.size
            val baseDist = 0.95f
            val tipDist = 1.25f
            val halfWidth = 0.07f

            verts.add(Vec3(baseCos * baseDist + perpCos * halfWidth, baseSin * baseDist + perpSin * halfWidth, 0.08f))
            verts.add(Vec3(baseCos * baseDist - perpCos * halfWidth, baseSin * baseDist - perpSin * halfWidth, 0.08f))
            verts.add(Vec3(baseCos * tipDist, baseSin * tipDist, 0.09f))

            faces.add(Face3D(pStart, pStart + 1, pStart + 2))
        }

        // Central Pivot & Alidade Rule Bar
        val barStart = verts.size
        val barLen = 1.45f
        val barW = 0.08f
        val barZ = 0.12f
        verts.add(Vec3(-barLen, -barW, barZ))
        verts.add(Vec3(barLen, -barW, barZ))
        verts.add(Vec3(barLen, barW, barZ))
        verts.add(Vec3(-barLen, barW, barZ))
        faces.add(Face3D(barStart, barStart + 1, barStart + 2))
        faces.add(Face3D(barStart, barStart + 2, barStart + 3))

        // Center jewel boss
        val bossStart = verts.size
        verts.add(Vec3(0f, 0f, barZ + 0.1f))
        val bossSegs = 8
        for (i in 0 until bossSegs) {
            val a = i * 2f * PI.toFloat() / bossSegs
            verts.add(Vec3(cos(a) * 0.18f, sin(a) * 0.18f, barZ))
        }
        for (i in 0 until bossSegs) {
            val next = (i + 1) % bossSegs
            faces.add(Face3D(bossStart, bossStart + 1 + i, bossStart + 1 + next))
        }

        return Mesh3D("Astrolabe de Damas", verts, faces)
    }

    // 1. Classical Apollo Bust: Pedestal, collarbone chest, neck, detailed sculpted head & curls
    private fun createApolloBustMesh(): Mesh3D {
        val verts = mutableListOf<Vec3>()
        val faces = mutableListOf<Face3D>()

        // Base Pedestal (Layer 0, 1)
        val baseRadius = 1.0f
        val numBaseSegments = 12
        for (i in 0 until numBaseSegments) {
            val angle = i * 2f * PI.toFloat() / numBaseSegments
            verts.add(Vec3(cos(angle) * baseRadius, -1.8f, sin(angle) * baseRadius))
            verts.add(Vec3(cos(angle) * (baseRadius * 0.85f), -1.5f, sin(angle) * (baseRadius * 0.85f)))
        }

        for (i in 0 until numBaseSegments) {
            val next = (i + 1) % numBaseSegments
            val v0 = i * 2
            val v1 = i * 2 + 1
            val v2 = next * 2
            val v3 = next * 2 + 1
            faces.add(Face3D(v0, v1, v2))
            faces.add(Face3D(v2, v1, v3))
        }

        // Chest / Torso (Layer 2, 3)
        val chestStart = verts.size
        for (i in 0 until numBaseSegments) {
            val angle = i * 2f * PI.toFloat() / numBaseSegments
            // Elliptical chest
            val rx = 1.3f * (1f + 0.2f * cos(angle))
            val rz = 0.8f * (1f - 0.2f * sin(angle))
            verts.add(Vec3(cos(angle) * rx, -1.0f, sin(angle) * rz))
            verts.add(Vec3(cos(angle) * (rx * 0.9f), -0.5f, sin(angle) * (rz * 0.85f)))
        }

        for (i in 0 until numBaseSegments) {
            val next = (i + 1) % numBaseSegments
            val v0 = chestStart + i * 2
            val v1 = chestStart + i * 2 + 1
            val v2 = chestStart + next * 2
            val v3 = chestStart + next * 2 + 1
            faces.add(Face3D(v0, v1, v2))
            faces.add(Face3D(v2, v1, v3))
        }

        // Neck (Layer 4)
        val neckStart = verts.size
        val neckRadius = 0.45f
        for (i in 0 until numBaseSegments) {
            val angle = i * 2f * PI.toFloat() / numBaseSegments
            verts.add(Vec3(cos(angle) * neckRadius, -0.1f, sin(angle) * neckRadius + 0.05f))
            verts.add(Vec3(cos(angle) * (neckRadius * 0.95f), 0.3f, sin(angle) * neckRadius + 0.05f))
        }

        for (i in 0 until numBaseSegments) {
            val next = (i + 1) % numBaseSegments
            val v0 = neckStart + i * 2
            val v1 = neckStart + i * 2 + 1
            val v2 = neckStart + next * 2
            val v3 = neckStart + next * 2 + 1
            faces.add(Face3D(v0, v1, v2))
            faces.add(Face3D(v2, v1, v3))
        }

        // Classical Head & Jaw (Layer 5, 6, 7)
        val headStart = verts.size
        val headHeights = listOf(0.4f, 0.75f, 1.1f, 1.45f, 1.7f)
        val headRadii = listOf(0.55f, 0.75f, 0.8f, 0.72f, 0.45f)
        val numHeadSlices = headHeights.size
        val numHeadSegs = 14

        for (h in 0 until numHeadSlices) {
            val y = headHeights[h]
            val r = headRadii[h]
            for (s in 0 until numHeadSegs) {
                val angle = s * 2f * PI.toFloat() / numHeadSegs
                // Face feature shaping: nose protrudes forward at z > 0 and y around 0.75-1.0
                var zMod = sin(angle) * r
                var xMod = cos(angle) * r
                if (zMod > 0 && h in 1..2) {
                    val noseFactor = if (s in 2..5) 0.35f else 0.1f
                    zMod += noseFactor
                }
                // Hair curls crowning the top
                if (h >= 3) {
                    val hairCurl = sin(angle * 4f) * 0.08f
                    xMod += hairCurl
                    zMod += hairCurl
                }
                verts.add(Vec3(xMod, y, zMod))
            }
        }

        for (h in 0 until numHeadSlices - 1) {
            val ring1 = headStart + h * numHeadSegs
            val ring2 = headStart + (h + 1) * numHeadSegs
            for (s in 0 until numHeadSegs) {
                val next = (s + 1) % numHeadSegs
                val v0 = ring1 + s
                val v1 = ring2 + s
                val v2 = ring1 + next
                val v3 = ring2 + next
                faces.add(Face3D(v0, v1, v2))
                faces.add(Face3D(v2, v1, v3))
            }
        }

        // Crown cap
        val crownApex = verts.size
        verts.add(Vec3(0f, 1.9f, 0f))
        val topRing = headStart + (numHeadSlices - 1) * numHeadSegs
        for (s in 0 until numHeadSegs) {
            val next = (s + 1) % numHeadSegs
            faces.add(Face3D(topRing + s, crownApex, topRing + next))
        }

        return Mesh3D("Buste d'Apollon", verts, faces)
    }

    // 2. Vénus Céleste: Graceful classical posture, draped curves
    private fun createVenusMesh(): Mesh3D {
        val verts = mutableListOf<Vec3>()
        val faces = mutableListOf<Face3D>()

        val levels = listOf(
            Triple(-1.8f, 0.9f, 0.0f),  // Base pedestal
            Triple(-1.4f, 0.6f, 0.1f),  // Feet / lower drape
            Triple(-0.9f, 0.75f, -0.1f), // Knees
            Triple(-0.3f, 0.7f, 0.15f),  // Thighs / drapery fold
            Triple(0.2f, 0.85f, -0.1f),  // Hips contrapposto
            Triple(0.7f, 0.55f, 0.0f),   // Waist
            Triple(1.1f, 0.75f, 0.05f),  // Bust & shoulders
            Triple(1.4f, 0.35f, 0.0f),   // Neck
            Triple(1.7f, 0.5f, 0.05f),   // Head tilted
            Triple(1.95f, 0.2f, 0.0f)    // Crown & chignon
        )

        val numSegs = 12
        for ((y, radius, xOffset) in levels) {
            for (i in 0 until numSegs) {
                val angle = i * 2f * PI.toFloat() / numSegs
                // Drapery folds rippling down the front and sides
                val drapeR = radius * (1.0f + 0.12f * sin(angle * 3f + y * 2f))
                verts.add(Vec3(cos(angle) * drapeR + xOffset, y, sin(angle) * drapeR))
            }
        }

        for (l in 0 until levels.size - 1) {
            val ring1 = l * numSegs
            val ring2 = (l + 1) * numSegs
            for (s in 0 until numSegs) {
                val next = (s + 1) % numSegs
                faces.add(Face3D(ring1 + s, ring2 + s, ring1 + next))
                faces.add(Face3D(ring1 + next, ring2 + s, ring2 + next))
            }
        }

        return Mesh3D("Vénus Céleste", verts, faces)
    }

    // 3. Le Penseur Polyédrique: Seated introspective figure on pedestal
    private fun createTheThinkerMesh(): Mesh3D {
        val verts = mutableListOf<Vec3>()
        val faces = mutableListOf<Face3D>()

        // Pedestal Rock (cube-like base)
        val baseIdx = verts.size
        val r = 1.2f
        verts.add(Vec3(-r, -1.8f, -r))
        verts.add(Vec3(r, -1.8f, -r))
        verts.add(Vec3(r, -1.8f, r))
        verts.add(Vec3(-r, -1.8f, r))
        verts.add(Vec3(-r * 0.9f, -1.0f, -r * 0.9f))
        verts.add(Vec3(r * 0.9f, -1.0f, -r * 0.9f))
        verts.add(Vec3(r * 0.9f, -1.0f, r * 0.9f))
        verts.add(Vec3(-r * 0.9f, -1.0f, r * 0.9f))

        // Pedestal faces
        faces.add(Face3D(baseIdx, baseIdx + 4, baseIdx + 1))
        faces.add(Face3D(baseIdx + 1, baseIdx + 4, baseIdx + 5))
        faces.add(Face3D(baseIdx + 1, baseIdx + 5, baseIdx + 2))
        faces.add(Face3D(baseIdx + 2, baseIdx + 5, baseIdx + 6))
        faces.add(Face3D(baseIdx + 2, baseIdx + 6, baseIdx + 3))
        faces.add(Face3D(baseIdx + 3, baseIdx + 6, baseIdx + 7))
        faces.add(Face3D(baseIdx + 3, baseIdx + 7, baseIdx + 0))
        faces.add(Face3D(baseIdx + 0, baseIdx + 7, baseIdx + 4))

        // Seated Torso & Bent Limbs (Polyhedral geometric representation)
        val bodyStart = verts.size
        // Pelvis on rock
        verts.add(Vec3(-0.4f, -0.9f, -0.2f)) // 0
        verts.add(Vec3(0.4f, -0.9f, -0.2f))  // 1
        verts.add(Vec3(0.4f, -0.9f, 0.4f))   // 2
        verts.add(Vec3(-0.4f, -0.9f, 0.4f))  // 3

        // Knees jutting forward
        verts.add(Vec3(-0.5f, -0.4f, 1.0f))  // 4 left knee
        verts.add(Vec3(0.3f, -0.3f, 1.1f))   // 5 right knee (elevated)

        // Arched Lower Back & Chest leaning forward
        verts.add(Vec3(-0.5f, -0.1f, -0.5f)) // 6 arched back
        verts.add(Vec3(0.5f, -0.1f, -0.5f))  // 7
        verts.add(Vec3(-0.4f, 0.4f, 0.1f))   // 8 chest
        verts.add(Vec3(0.4f, 0.4f, 0.1f))    // 9

        // Broad Shoulders
        verts.add(Vec3(-0.8f, 0.7f, -0.1f))  // 10 left shoulder
        verts.add(Vec3(0.8f, 0.7f, -0.1f))   // 11 right shoulder

        // Elbow resting on knee
        verts.add(Vec3(0.4f, 0.0f, 0.9f))    // 12 right elbow on right knee
        verts.add(Vec3(0.1f, 0.5f, 0.6f))    // 13 hand to chin

        // Bowed Head
        verts.add(Vec3(0.0f, 0.9f, 0.4f))    // 14 chin / face
        verts.add(Vec3(0.0f, 1.3f, 0.1f))    // 15 top of head
        verts.add(Vec3(-0.3f, 1.1f, 0.2f))   // 16 left temple
        verts.add(Vec3(0.3f, 1.1f, 0.2f))    // 17 right temple

        // Build anatomical triangles
        val bFaces = listOf(
            Face3D(bodyStart + 0, bodyStart + 1, bodyStart + 6),
            Face3D(bodyStart + 1, bodyStart + 7, bodyStart + 6),
            Face3D(bodyStart + 6, bodyStart + 7, bodyStart + 10),
            Face3D(bodyStart + 7, bodyStart + 11, bodyStart + 10),
            Face3D(bodyStart + 10, bodyStart + 8, bodyStart + 11),
            Face3D(bodyStart + 11, bodyStart + 8, bodyStart + 9),
            // Legs
            Face3D(bodyStart + 3, bodyStart + 4, bodyStart + 0),
            Face3D(bodyStart + 2, bodyStart + 1, bodyStart + 5),
            Face3D(bodyStart + 4, bodyStart + 8, bodyStart + 2),
            Face3D(bodyStart + 5, bodyStart + 12, bodyStart + 9),
            // Arm to chin
            Face3D(bodyStart + 11, bodyStart + 12, bodyStart + 9),
            Face3D(bodyStart + 12, bodyStart + 13, bodyStart + 14),
            // Head
            Face3D(bodyStart + 14, bodyStart + 17, bodyStart + 15),
            Face3D(bodyStart + 14, bodyStart + 15, bodyStart + 16),
            Face3D(bodyStart + 16, bodyStart + 10, bodyStart + 15),
            Face3D(bodyStart + 17, bodyStart + 15, bodyStart + 11)
        )
        faces.addAll(bFaces)

        return Mesh3D("Le Penseur Polyédrique", verts, faces)
    }

    // 4. Victoire Ailée de Samothrace: Outspread dynamic wings and prow
    private fun createWingedVictoryMesh(): Mesh3D {
        val verts = mutableListOf<Vec3>()
        val faces = mutableListOf<Face3D>()

        // Ship Prow Pedestal
        val prowStart = verts.size
        verts.add(Vec3(0.0f, -1.8f, 1.5f))   // sharp prow tip
        verts.add(Vec3(-1.0f, -1.4f, -0.8f)) // left stern
        verts.add(Vec3(1.0f, -1.4f, -0.8f))  // right stern
        verts.add(Vec3(0.0f, -1.1f, 1.2f))   // deck prow
        verts.add(Vec3(-0.7f, -0.9f, -0.5f)) // left deck
        verts.add(Vec3(0.7f, -0.9f, -0.5f))  // right deck

        faces.add(Face3D(prowStart + 0, prowStart + 3, prowStart + 1))
        faces.add(Face3D(prowStart + 1, prowStart + 3, prowStart + 4))
        faces.add(Face3D(prowStart + 0, prowStart + 2, prowStart + 3))
        faces.add(Face3D(prowStart + 3, prowStart + 2, prowStart + 5))

        // Torso surging forward
        val torsoStart = verts.size
        verts.add(Vec3(-0.3f, -0.7f, 0.1f))  // 0
        verts.add(Vec3(0.3f, -0.7f, 0.1f))   // 1
        verts.add(Vec3(-0.4f, 0.0f, 0.4f))   // 2 belly forward
        verts.add(Vec3(0.4f, 0.0f, 0.4f))    // 3
        verts.add(Vec3(-0.5f, 0.6f, 0.6f))   // 4 chest thrust forward
        verts.add(Vec3(0.5f, 0.6f, 0.6f))    // 5
        verts.add(Vec3(-0.4f, 0.9f, 0.4f))   // 6 neck/shoulders
        verts.add(Vec3(0.4f, 0.9f, 0.4f))    // 7

        faces.add(Face3D(torsoStart + 0, torsoStart + 2, torsoStart + 1))
        faces.add(Face3D(torsoStart + 1, torsoStart + 2, torsoStart + 3))
        faces.add(Face3D(torsoStart + 2, torsoStart + 4, torsoStart + 3))
        faces.add(Face3D(torsoStart + 3, torsoStart + 4, torsoStart + 5))
        faces.add(Face3D(torsoStart + 4, torsoStart + 6, torsoStart + 5))
        faces.add(Face3D(torsoStart + 5, torsoStart + 6, torsoStart + 7))

        // Left Wing (Magnificent feather sweeps)
        val lWingStart = verts.size
        verts.add(Vec3(-0.5f, 0.7f, 0.0f))   // wing root
        verts.add(Vec3(-1.4f, 1.2f, -0.5f))  // mid wing
        verts.add(Vec3(-2.0f, 1.8f, -1.0f))  // wing tip apex
        verts.add(Vec3(-1.8f, 1.1f, -0.8f))  // lower feathers
        verts.add(Vec3(-1.1f, 0.5f, -0.4f))  // inner feathers

        faces.add(Face3D(lWingStart + 0, lWingStart + 1, lWingStart + 4))
        faces.add(Face3D(lWingStart + 1, lWingStart + 2, lWingStart + 3))
        faces.add(Face3D(lWingStart + 1, lWingStart + 3, lWingStart + 4))

        // Right Wing
        val rWingStart = verts.size
        verts.add(Vec3(0.5f, 0.7f, 0.0f))
        verts.add(Vec3(1.4f, 1.2f, -0.5f))
        verts.add(Vec3(2.0f, 1.8f, -1.0f))
        verts.add(Vec3(1.8f, 1.1f, -0.8f))
        verts.add(Vec3(1.1f, 0.5f, -0.4f))

        faces.add(Face3D(rWingStart + 0, rWingStart + 4, rWingStart + 1))
        faces.add(Face3D(rWingStart + 1, rWingStart + 3, rWingStart + 2))
        faces.add(Face3D(rWingStart + 1, rWingStart + 4, rWingStart + 3))

        return Mesh3D("Victoire Ailée de Samothrace", verts, faces)
    }

    // 5. Parametric Mobius Strip Sculpture
    private fun createMobiusStripMesh(): Mesh3D {
        val verts = mutableListOf<Vec3>()
        val faces = mutableListOf<Face3D>()

        val uSegments = 32
        val vSegments = 4
        val radius = 1.2f
        val width = 0.5f

        for (i in 0 until uSegments) {
            val u = i * 2f * PI.toFloat() / uSegments
            for (j in 0..vSegments) {
                val v = -width + 2f * width * (j.toFloat() / vSegments)
                val x = (radius + v * cos(u / 2f)) * cos(u)
                val z = (radius + v * cos(u / 2f)) * sin(u)
                val y = v * sin(u / 2f)
                verts.add(Vec3(x, y, z))
            }
        }

        val stride = vSegments + 1
        for (i in 0 until uSegments) {
            val nextI = (i + 1) % uSegments
            for (j in 0 until vSegments) {
                val v0 = i * stride + j
                val v1 = i * stride + (j + 1)
                // Mobius strip twist at loop close: reverse orientation
                val v2 = if (nextI == 0) nextI * stride + (vSegments - j) else nextI * stride + j
                val v3 = if (nextI == 0) nextI * stride + (vSegments - (j + 1)) else nextI * stride + (j + 1)

                faces.add(Face3D(v0, v1, v2))
                faces.add(Face3D(v2, v1, v3))
            }
        }

        return Mesh3D("Ruban d'Infini Géométrique", verts, faces)
    }
}

object SculptureRenderer {

    fun projectAndRender(
        mesh: Mesh3D,
        pitchRad: Float,
        yawRad: Float,
        zoomScale: Float,
        canvasWidth: Float,
        canvasHeight: Float,
        lightAngleRad: Float,
        material: SculptureMaterial,
        wireframeMode: Boolean
    ): List<ProjectedFace> {
        val centerX = canvasWidth / 2f
        val centerY = canvasHeight / 2f
        val baseScale = min(canvasWidth, canvasHeight) * 0.28f * zoomScale

        // Transform vertices
        val transformed = mesh.vertices.map { v ->
            v.rotateY(yawRad).rotateX(pitchRad)
        }

        // Spotlight direction vector
        val lightDir = Vec3(
            cos(lightAngleRad) * 0.8f,
            0.9f,
            sin(lightAngleRad) * 0.8f + 0.6f
        ).normalize()

        // Eye view vector in world space (looking down -Z towards +Z)
        val eyeDir = Vec3(0f, 0f, 1f)

        // Process faces
        val projectedFaces = mutableListOf<ProjectedFace>()

        for (face in mesh.faces) {
            if (face.a >= transformed.size || face.b >= transformed.size || face.c >= transformed.size) continue
            val va = transformed[face.a]
            val vb = transformed[face.b]
            val vc = transformed[face.c]

            // Calculate face normal
            val edge1 = vb - va
            val edge2 = vc - va
            var normal = edge1.cross(edge2).normalize()

            // Normal alignment with light
            val nDotL = normal.dot(lightDir)
            val diff = max(0f, nDotL)

            // Specular reflection
            // R = 2*(N.L)*N - L
            val rVec = (normal * (2f * nDotL) - lightDir).normalize()
            val spec = max(0f, rVec.dot(eyeDir)).pow(12f) * material.specularStrength

            // Average depth for Painter's algorithm
            val avgZ = (va.z + vb.z + vc.z) / 3f

            // Perspective factor
            val dist = 5.0f
            val pa = Offset(centerX + (va.x / (dist - va.z * 0.2f)) * baseScale, centerY - (va.y / (dist - va.z * 0.2f)) * baseScale)
            val pb = Offset(centerX + (vb.x / (dist - vb.z * 0.2f)) * baseScale, centerY - (vb.y / (dist - vb.z * 0.2f)) * baseScale)
            val pc = Offset(centerX + (vc.x / (dist - vc.z * 0.2f)) * baseScale, centerY - (vc.y / (dist - vc.z * 0.2f)) * baseScale)

            // Color shading based on material
            val baseColor = Color(material.baseColorHex)
            val ambient = 0.25f
            val illumination = (ambient + diff * 0.75f).coerceIn(0f, 1.5f)

            val shadedColor = when (material) {
                SculptureMaterial.DAMASCUS_BRASS -> {
                    // Golden Damascus brass with warm amber highlights
                    val r = (baseColor.red * illumination + spec * 0.95f).coerceIn(0f, 1f)
                    val g = (baseColor.green * illumination + spec * 0.8f).coerceIn(0f, 1f)
                    val b = (baseColor.blue * illumination + spec * 0.25f).coerceIn(0f, 1f)
                    Color(r, g, b, 1f)
                }
                SculptureMaterial.LAPIS_LAZULI -> {
                    // Deep Babylonian cobalt lapis with golden glints
                    val r = (baseColor.red * illumination + spec * 0.75f).coerceIn(0f, 1f)
                    val g = (baseColor.green * illumination + spec * 0.65f).coerceIn(0f, 1f)
                    val b = (baseColor.blue * (illumination * 1.15f) + spec * 0.4f).coerceIn(0f, 1f)
                    Color(r, g, b, 1f)
                }
                SculptureMaterial.CARRARA_MARBLE -> {
                    // Soft warm translucent ivory with subtle warm undertones
                    val r = (baseColor.red * illumination + spec * 0.6f).coerceIn(0f, 1f)
                    val g = (baseColor.green * illumination + spec * 0.58f).coerceIn(0f, 1f)
                    val b = (baseColor.blue * (illumination * 0.95f) + spec * 0.55f).coerceIn(0f, 1f)
                    Color(r, g, b, 1f)
                }
                SculptureMaterial.IMPERIAL_GOLD -> {
                    // Deep rich golden gleam with intense specular highlights
                    val r = (baseColor.red * illumination + spec * 0.95f).coerceIn(0f, 1f)
                    val g = (baseColor.green * illumination + spec * 0.85f).coerceIn(0f, 1f)
                    val b = (baseColor.blue * illumination + spec * 0.35f).coerceIn(0f, 1f)
                    Color(r, g, b, 1f)
                }
                SculptureMaterial.BRONZE_PATINA -> {
                    // Burnished bronze with warm highlights and greenish depth
                    val r = (baseColor.red * illumination + spec * 0.4f).coerceIn(0f, 1f)
                    val g = (baseColor.green * (illumination * 1.05f) + spec * 0.45f).coerceIn(0f, 1f)
                    val b = (baseColor.blue * illumination + spec * 0.3f).coerceIn(0f, 1f)
                    Color(r, g, b, 1f)
                }
                SculptureMaterial.GLOWING_WIREFRAME -> {
                    // Holographic cyan/gold grid
                    val alpha = (0.2f + diff * 0.5f).coerceIn(0.15f, 0.85f)
                    Color(0.2f, 0.65f, 1.0f, alpha)
                }
            }

            val wireColor = when (material) {
                SculptureMaterial.GLOWING_WIREFRAME -> Color(0xFF64B5F6)
                SculptureMaterial.IMPERIAL_GOLD -> Color(0x66D4AF37)
                SculptureMaterial.DAMASCUS_BRASS -> Color(0x66C9A042)
                SculptureMaterial.LAPIS_LAZULI -> Color(0x554169E1)
                else -> Color(0x33000000)
            }

            projectedFaces.add(ProjectedFace(pa, pb, pc, avgZ, shadedColor, wireColor))
        }

        // Painter's Algorithm: Sort faces from furthest to nearest
        return projectedFaces.sortedBy { it.avgZ }
    }
}
