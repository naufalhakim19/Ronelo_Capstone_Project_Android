package com.example.ronelo

import com.example.ronelo.model.ModelNews
import com.example.ronelo.model.ModelNurse
import com.example.ronelo.model.ModelPatient
import com.example.ronelo.model.ResultMedicineModel

object Dummy {
    fun dataNurse(): List<ModelNurse> {
        val nurse = ArrayList<ModelNurse>()

        nurse.add(
            ModelNurse(
                "nurse1",
                "Putri",
                "Cupid Hospital",
                "+62 87799123995",
                "https://www.danwarnerphotography.com/wp-content/uploads/2016/05/MGM1503-eyebrow-fave-555px-.jpg"
            )
        )
        nurse.add(
            ModelNurse(
                "nurse2",
                "Alice",
                "Omnaw Hospital",
                "+6289771230002",
                "https://www.unitex.com/wp-content/uploads/2018/04/Unitex-Nursing-Shortage-1.jpg"
            )
        )
        nurse.add(
            ModelNurse(
                "nurse3",
                "Kimmy",
                "Titra Hospital",
                "+628123777900",
                "https://acumen-nurses.com/nursepage1.png"
            )
        )
        nurse.add(
            ModelNurse(
                "nurse4",
                "Fanny",
                "Ciloam Hospital",
                "+62899123555",
                "https://i.pinimg.com/474x/b1/78/5d/b1785dc51d2bc08453ff812b197a5b59.jpg"
            )
        )
        nurse.add(
            ModelNurse(
                "nurse5",
                "Diggie",
                "Jaqi Hospital",
                "+62899912030",
                "https://i.pinimg.com/originals/59/08/cc/5908ccbcf2bd055e10e929a74a396dce.jpg"
            )
        )
        nurse.add(
            ModelNurse(
                "nurse5",
                "Demi",
                "Jarnot Hospital",
                "+62899977730",
                "https://3.imimg.com/data3/VP/AG/MY-10673539/nurse-female-250x250.jpg"
            )
        )
        return nurse
    }

    fun dataNews(): List<ModelNews> {
        val news = ArrayList<ModelNews>()

        news.add(
            ModelNews(
                "https://dinkes.papua.go.id/wp-content/uploads/2020/12/thumbnail-1-Des.png",
                "Update Data Corona 2021!"
            )
        )
        news.add(
            ModelNews(
                "https://rm.id/config/thumbnail.php?file=../images/berita/med/stok-vaksin-covid19-menipis-perketat-protokol-kesehatan-prioritaskan-vaksinasi-lansia_73498.jpeg&width=739&height=385",
                "Daftar Vaksin Sekarang!"
            )
        )
        news.add(
            ModelNews(
                "https://sehatnegeriku.kemkes.go.id/wp-content/uploads/2020/03/Untitled-20.png",
                "Cara mencegah Corona!"
            )
        )
        return news
    }

    fun dataPatient(): List<ModelPatient> {
        val patient = ArrayList<ModelPatient>()

        patient.add(
            ModelPatient(
                "https://cdn2.iconfinder.com/data/icons/virus-14/512/user_male_mask_medical_mask_healthcare_avatar-512.png",
                "Udin Nadin",
                "36",
                "Male",
                "Influenza",
                "Pasien sedang pilek"

            )
        )
        return patient
    }

    fun dataMedicine() {
        val medicine = ArrayList<ResultMedicineModel>()

        medicine.add(
            ResultMedicineModel(
                "AMLODIPINE",
                "Tiap tablet mengandung Amlodipine 5 mg",
                "Pasien yang hipersensitif terhadap amlodipine dan golongan dihydropirydine lainnya.",
                "AMLODIPINE merupakan obat antihipertensi golongan Calcium Channel Blockers (CCB). Obat ini bekerja dengan cara menghambat kalsium masuk ke dalam sel sehingga salah satu efeknya adalah menyebabkan vasodilatasi, memperlambat laju jantung, dan menurunkan kontraktilitas miokard sehingga menurunkan tekanan darah. Dalam penggunaan obat ini harus SESUAI DENGAN PETUNJUK DOKTER.",
                "PENGGUNAAN OBAT INI HARUS SESUAI DENGAN PETUNJUK DOKTER.\nDosis awal 5 mg per hari. Maksimal 10 mg per hari.\nTitrasi dosis dilakukan tiap 7-14 hari.",
                "Dikonsumsi sebelum atau sesudah makan. ",
                "Pemakaian obat umumnya memiliki efek samping tertentu dan sesuai dengan masing-masing individu. Jika terjadi efek samping yang berlebih dan berbahaya, harap konsultasikan kepada tenaga medis.\nEfek samping yang mungkin terjadi dalam penggunaan obat adalah: \nUmum yang sering timbul: edema dan sakit kepala.",
                "HARUS DENGAN RESEP DOKTER. \nPengawasan ketat pada pasien gangguan fungsi hati dan gagal jantung kongestif."
            )
        )}
}