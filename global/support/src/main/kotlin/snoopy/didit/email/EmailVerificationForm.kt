package snoopy.didit.email

val EMAIL_VERIFICATION_FORM =
    """
    <!DOCTYPE html>
    <html style="margin: 0; padding: 0;">
        <head>
            <meta charset="UTF-8">
            <title>이메일 인증</title>
        </head>
        <body style="margin: 0; padding: 0; background-color: #f4f4f4; font-family: Arial, sans-serif;">
            <table role="presentation" cellpadding="0" cellspacing="0" style="width: 100%; margin: auto; background-color: #f4f4f4; padding: 20px;">
                <tr>
                    <td>
                        <table role="presentation" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; padding: 40px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                            <tr>
                                <td>
                                    <div style="text-align: center; margin-bottom: 30px;">
                                        <img src="회사로고URL" alt="로고" style="max-width: 150px; height: auto;">
                                    </div>
                                    <h1 style="color: #333333; font-size: 24px; font-weight: bold; margin-bottom: 20px; text-align: center;">이메일 주소 인증</h1>
                                    <p style="color: #666666; font-size: 16px; line-height: 24px; margin-bottom: 30px;">
                                        안녕하세요.<br>
                                        회원가입을 위한 이메일 주소 인증을 진행해 주세요.<br>
                                        아래의 인증번호를 입력하시면 인증이 완료됩니다.
                                    </p>
                                    <div style="background-color: #f8f9fa; border-radius: 4px; padding: 20px; text-align: center; margin-bottom: 30px;">
                                        <h2 style="color: #333333; font-size: 20px; margin: 0 0 10px 0;">인증번호</h2>
                                        <p style="color: #4a90e2; font-size: 32px; font-weight: bold; margin: 0; letter-spacing: 4px;">%code%</p>
                                    </div>
                                    <p style="color: #999999; font-size: 14px; line-height: 20px; margin-bottom: 0;">
                                        • 본 인증번호는 5분간 유효합니다.<br>
                                        • 인증번호는 1회만 사용 가능합니다.<br>
                                        • 본 메일은 발신전용입니다.
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid #eeeeee; text-align: center;">
                                        <p style="color: #999999; font-size: 13px; line-height: 18px; margin: 0;">
                                            © 2025 Didit Inc. All rights reserved.<br>
                                        </p>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
    </html>
    """.trimIndent()
