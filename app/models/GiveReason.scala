/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package models

import play.api.data.Form
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Aliases.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.RadioItem

sealed trait GiveReason

object GiveReason {

  val baseMessageKey: String = "giveReason"

  case object CheckTaxCode extends WithName("checkTaxCode") with GiveReason
  case object CheckTaxYear extends WithName("checkTaxYear") with GiveReason
  case object CheckTaxPaid extends WithName("checkTaxPaid") with GiveReason
  case object ClaimTaxBack extends WithName("claimTaxBack") with GiveReason
  case object ContactAboutP800 extends WithName("contactAboutP800") with GiveReason
  case object P800Wrong extends WithName("p800Wrong") with GiveReason
  case object PayOwedTax extends WithName("payOwedTax") with GiveReason
  case object ProgressChasing extends WithName("progressChasing") with GiveReason
  case object Other extends WithName("other") with GiveReason

  val values: Seq[GiveReason] = Seq(
    CheckTaxCode,
    CheckTaxYear,
    CheckTaxPaid,
    ClaimTaxBack,
    ContactAboutP800,
    P800Wrong,
    PayOwedTax,
    ProgressChasing,
    Other
  )

  def options(form: Form[_])(implicit messages: Messages): Seq[RadioItem] = values.map { value =>
    RadioItem(
      id = Some(value.toString),
      value = Some(value.toString),
      content = Text(messages(s"$baseMessageKey.$value")),
      checked = form.hasErrors
    )
  }

  implicit val enumerable: Enumerable[GiveReason] =
    Enumerable(values.map(v => v.toString -> v): _*)
}
